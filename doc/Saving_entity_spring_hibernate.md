## About entity in connections

Firstly we have 4 entity to save to database. 

### Now I have:
- Book
- Publisher
- Author
- Genre

| From      | To   | Cascade        |
|-----------|------|----------------|
| Publisher | Book | ALL            |
| Author    | Book | PERSIST, MERGE |
