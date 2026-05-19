"""Concrete processors and abstract factory support for Python."""

from __future__ import annotations

from abc import ABC, abstractmethod
from typing import Callable

from report_processing.report_domain import ReportCategory, ReportType


class ReportProcessor(ABC):
    """Common processing contract."""

    @abstractmethod
    def process(self) -> str:
        """Return the simulated processing message."""


class AbstractReportProcessor(ReportProcessor, ABC):
    """Shared message formatting logic for concrete processors."""

    def __init__(
        self,
        file_name: str,
        category: ReportCategory,
        report_type: ReportType,
    ) -> None:
        self._file_name = file_name
        self._category = category
        self._report_type = report_type

    def process(self) -> str:
        return (
            f"Procesando informe '{self._file_name}' de {self._category.display_name} y tipo "
            f"{self._report_type.display_name}. Acción simulada: {self.action_description()}."
        )

    @abstractmethod
    def action_description(self) -> str:
        """Describe the domain-specific simulated action."""


class EntryInvoiceReportProcessor(AbstractReportProcessor):
    def __init__(self, file_name: str) -> None:
        super().__init__(file_name, ReportCategory.ENTRY, ReportType.INVOICE)

    def action_description(self) -> str:
        return "registro de factura recibida en el módulo de compras"


class EntryPurchaseReportProcessor(AbstractReportProcessor):
    def __init__(self, file_name: str) -> None:
        super().__init__(file_name, ReportCategory.ENTRY, ReportType.PURCHASE)

    def action_description(self) -> str:
        return "validación de compra entrante para conciliación interna"


class EntryOrderReportProcessor(AbstractReportProcessor):
    def __init__(self, file_name: str) -> None:
        super().__init__(file_name, ReportCategory.ENTRY, ReportType.ORDER)

    def action_description(self) -> str:
        return "alta de pedido de entrada para planificación logística"


class OutputInvoiceReportProcessor(AbstractReportProcessor):
    def __init__(self, file_name: str) -> None:
        super().__init__(file_name, ReportCategory.OUTPUT, ReportType.INVOICE)

    def action_description(self) -> str:
        return "emisión de factura de salida hacia el sistema contable"


class OutputPurchaseReportProcessor(AbstractReportProcessor):
    def __init__(self, file_name: str) -> None:
        super().__init__(file_name, ReportCategory.OUTPUT, ReportType.PURCHASE)

    def action_description(self) -> str:
        return "cierre de compra de salida para auditoría de movimientos"


class MixedInvoiceReportProcessor(AbstractReportProcessor):
    def __init__(self, file_name: str) -> None:
        super().__init__(file_name, ReportCategory.MIXED, ReportType.INVOICE)

    def action_description(self) -> str:
        return "orquestación híbrida de una factura compartida entre entrada y salida"


class MixedPurchaseReportProcessor(AbstractReportProcessor):
    def __init__(self, file_name: str) -> None:
        super().__init__(file_name, ReportCategory.MIXED, ReportType.PURCHASE)

    def action_description(self) -> str:
        return "sincronización de compra mixta entre inventario y expedición"


class CategoryReportFactory(ABC):
    """Abstract factory contract for Python, intentionally avoiding ``Protocol``."""

    @abstractmethod
    def supported_category(self) -> ReportCategory:
        """Return the family represented by this factory."""

    @abstractmethod
    def create_processor(self, report_type: ReportType, file_name: str) -> ReportProcessor:
        """Create the appropriate processor for the requested type."""


class MapBackedReportFactory(CategoryReportFactory, ABC):
    """Registry-based factory shared by all concrete categories."""

    def __init__(self) -> None:
        self._builders: dict[ReportType, Callable[[str], ReportProcessor]] = {}

    def register(
        self,
        report_type: ReportType,
        builder: Callable[[str], ReportProcessor],
    ) -> None:
        self._builders[report_type] = builder

    def create_processor(self, report_type: ReportType, file_name: str) -> ReportProcessor:
        builder = self._builders.get(report_type)
        if builder is None:
            raise ValueError(
                f"The category {self.supported_category().display_name} does not support "
                f"reports of type {report_type.display_name}."
            )
        return builder(file_name)


class EntryReportFactory(MapBackedReportFactory):
    def __init__(self) -> None:
        super().__init__()
        self.register(ReportType.INVOICE, EntryInvoiceReportProcessor)
        self.register(ReportType.PURCHASE, EntryPurchaseReportProcessor)
        self.register(ReportType.ORDER, EntryOrderReportProcessor)

    def supported_category(self) -> ReportCategory:
        return ReportCategory.ENTRY


class OutputReportFactory(MapBackedReportFactory):
    def __init__(self) -> None:
        super().__init__()
        self.register(ReportType.INVOICE, OutputInvoiceReportProcessor)
        self.register(ReportType.PURCHASE, OutputPurchaseReportProcessor)

    def supported_category(self) -> ReportCategory:
        return ReportCategory.OUTPUT


class MixedReportFactory(MapBackedReportFactory):
    def __init__(self) -> None:
        super().__init__()
        self.register(ReportType.INVOICE, MixedInvoiceReportProcessor)
        self.register(ReportType.PURCHASE, MixedPurchaseReportProcessor)

    def supported_category(self) -> ReportCategory:
        return ReportCategory.MIXED

