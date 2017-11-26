# Polish legal docs parser
OOP class assignment. Parser which turns text file with legal document (following Act of rules for legislative techniques in Poland) into object form.

Intensively uses functional features of Java 8 such as streams, lambdas, parallelism, method references.

## Partition levels

Chapter (Rozdział) `Rozdział [IVX]+\\nTITLE\\n`

Section (Sekcja) `[A-ZĘÓĄŚŁŻŹĆŃ ]+\\n`

Article (Artykuł) `Art\\. \\d+\\.\\n`

Paragraph (Ustęp) `\\d+\\.\\s`

Point (Punkt) `\\d+\\)\\s`

Letter (Litera) `[a-z]+\\)\\s`

Indent (Tiret) `- \\s`

## Running
Not yet

## Contributing
Better not