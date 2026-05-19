"""Core domain objects shared by the Python implementation."""

from __future__ import annotations

from dataclasses import dataclass
from enum import Enum


class ReportCategory(Enum):
    """Supported categories and their external file prefix."""

    ENTRY = ("ENT", "ENTRADA")
    OUTPUT = ("SAL", "SALIDA")
    MIXED = ("MIX", "MIXTA")

    def __init__(self, code: str, display_name: str) -> None:
        self.code = code
        self.display_name = display_name

    @classmethod
    def from_code(cls, code: str) -> "ReportCategory":
        for category in cls:
            if category.code == code:
                return category
        raise ValueError(f"Unsupported report category: {code}")


class ReportType(Enum):
    """Supported types, including the evolved ORDER type."""

    INVOICE = ("FAC", "FACTURA")
    PURCHASE = ("COM", "COMPRA")
    ORDER = ("PED", "PEDIDO")

    def __init__(self, code: str, display_name: str) -> None:
        self.code = code
        self.display_name = display_name

    @classmethod
    def from_code(cls, code: str) -> "ReportType":
        for report_type in cls:
            if report_type.code == code:
                return report_type
        raise ValueError(f"Unsupported report type: {code}")


@dataclass(frozen=True)
class ReportMetadata:
    """Immutable result of parsing a report filename."""

    category: ReportCategory
    report_type: ReportType
    file_name: str

