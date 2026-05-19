"""Executable entry point for the Python reference implementation."""

from report_processing.service import ReportProcessingService


def main() -> None:
    reports = [
        "ENT_FAC_001.txt",
        "ENT_COM_002.txt",
        "SAL_FAC_003.txt",
        "SAL_COM_004.txt",
        "MIX_FAC_005.txt",
        "MIX_COM_006.txt",
        "ENT_PED_007.txt",
        "SAL_PED_008.txt",
    ]

    service = ReportProcessingService()
    for message in service.process_reports(reports):
        print(message)


if __name__ == "__main__":
    main()

