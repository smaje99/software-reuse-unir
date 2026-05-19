REPORT=report

.PHONY: all uml clean

all: uml $(REPORT).pdf

uml:
	dot -Tpng uml/base.dot -o images/uml-base.png
	dot -Tpng uml/mixed-evolution.dot -o images/uml-mixed-evolution.png
	dot -Tpng uml/order-evolution.dot -o images/uml-order-evolution.png

$(REPORT).pdf: $(REPORT).tex bib/references.bib uml
	pdflatex -interaction=nonstopmode $(REPORT).tex
	bibtex $(REPORT)
	pdflatex -interaction=nonstopmode $(REPORT).tex
	pdflatex -interaction=nonstopmode $(REPORT).tex

clean:
	rm -f $(REPORT).aux $(REPORT).bbl $(REPORT).blg $(REPORT).log $(REPORT).out $(REPORT).pdf $(REPORT).toc

