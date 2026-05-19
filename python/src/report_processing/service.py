"""Application service for the Python implementation."""

from report_processing.report_domain import ReportCategory
from report_processing.report_parser import ReportParser
from report_processing.report_processors import (
    CategoryReportFactory,
    EntryReportFactory,
    MixedReportFactory,
    OutputReportFactory,
)


class ReportFactoryProvider:
    """Central registry of factories indexed by category."""

    def __init__(self) -> None:
        self._factories: dict[ReportCategory, CategoryReportFactory] = {}
        self._register(EntryReportFactory())
        self._register(OutputReportFactory())
        self._register(MixedReportFactory())

    def _register(self, factory: CategoryReportFactory) -> None:
        self._factories[factory.supported_category()] = factory

    def get_factory(self, category: ReportCategory) -> CategoryReportFactory:
        try:
            return self._factories[category]
        except KeyError as error:
            raise ValueError(f"No factory registered for category {category}") from error


class ReportProcessingService:
    """Coordinates parsing, factory resolution and processing."""

    def __init__(self) -> None:
        self._parser = ReportParser()
        self._provider = ReportFactoryProvider()

    def process_reports(self, report_file_names: list[str]) -> list[str]:
        results: list[str] = []
        for report_file_name in report_file_names:
            try:
                metadata = self._parser.parse(report_file_name)
                factory = self._provider.get_factory(metadata.category)
                processor = factory.create_processor(metadata.report_type, metadata.file_name)
                results.append(processor.process())
            except ValueError as error:
                results.append(f"Error procesando '{report_file_name}': {error}")
        return results

