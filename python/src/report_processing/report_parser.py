"""Filename parser for the Python implementation."""

from report_processing.report_domain import ReportCategory, ReportMetadata, ReportType


class ReportParser:
    """Validates and parses filenames shaped as ``CAT_TIPO_nombre.txt``."""

    def parse(self, file_name: str) -> ReportMetadata:
        if not file_name or not file_name.strip():
            raise ValueError("The report file name cannot be null or blank.")
        if not file_name.endswith(".txt"):
            raise ValueError(f"The report file name must end with .txt: {file_name}")

        tokens = file_name[:-4].split("_")
        if len(tokens) < 3:
            raise ValueError(
                f"The report file name must follow CAT_TIPO_nombre.txt: {file_name}"
            )

        return ReportMetadata(
            category=ReportCategory.from_code(tokens[0]),
            report_type=ReportType.from_code(tokens[1]),
            file_name=file_name,
        )

