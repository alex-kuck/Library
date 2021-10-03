# Library

## Getting Started

### Starting the Server Locally

```shell
make start
```

### Running Tests

```shell
make test
```

## Possible Improvements

Some corners have been cut, due to the limited time frame. Some possible (and recommended) improvements are listed below:

* Cleanup data sources
  * Use DB instead of CSV files
  * References via ID instead of full name
  * Move filtering/selection logic down into repositories where possible
* Data Querying
  * Use query objects (possible GraphQL) instead of long URLs with query parameters
* Mapping
  * Possible to consider a library, e.g. MapStruct
  * Introduction of dedicated domain model between entities and API-models
  * Dedicated mappers instead of mapping in services
