## [1.1.2](https://github.com/oliver-Victorxavier/product-catalog/compare/v1.1.1...v1.1.2) (2025-08-19)


### 🐛 Bug Fixes

* correct dockerhub username to victorxavie ([1189304](https://github.com/oliver-Victorxavier/product-catalog/commit/1189304e46795b1c9be1038d80b2536822a4d3c7))

## [1.1.1](https://github.com/oliver-Victorxavier/product-catalog/compare/v1.1.0...v1.1.1) (2025-08-19)


### 🐛 Bug Fixes

* correct dockerhub username from victorxavie to oliver-victorxavier ([74ecea0](https://github.com/oliver-Victorxavier/product-catalog/commit/74ecea01ea68e37ea8f9d360a1ea604aa6f38c05))

## [1.1.0](https://github.com/oliver-Victorxavier/product-catalog/compare/v1.0.0...v1.1.0) (2025-08-19)


### 🚀 Features

* add cart and redis configuration ([cc9029e](https://github.com/oliver-Victorxavier/product-catalog/commit/cc9029ecdc16a4a3260afe983064b96695c682cb))
* add cart controller with security improvements ([55c6bf4](https://github.com/oliver-Victorxavier/product-catalog/commit/55c6bf46515bc28469c3d11e98c839bcf11b48ee))
* add cart domain layer and application services ([0e5d32b](https://github.com/oliver-Victorxavier/product-catalog/commit/0e5d32b6bf5b8e2dd12c353c8180a979481a7a56))
* add cart domain models and DTOs ([76bb64f](https://github.com/oliver-Victorxavier/product-catalog/commit/76bb64fe8d6cf7ca0b33f3ee8f879ddbcc200057))
* add cart management use cases ([63b6362](https://github.com/oliver-Victorxavier/product-catalog/commit/63b6362d5a9e0b12eae6dbf7e8d7ca52c3571ed2))
* add cart redis entities and mapper ([32f3c88](https://github.com/oliver-Victorxavier/product-catalog/commit/32f3c8885fc5e4e0e332211523de514dca2517a0))
* add cart repository implementation ([9111387](https://github.com/oliver-Victorxavier/product-catalog/commit/911138721ff026425e45a8ae26260fb4bd50dfcd))

## 1.0.0 (2025-08-16)


### 🚀 Features

* **adapters:** implements repository adapter layer — Creates ProductRepositoryImpl and CategoryRepositoryImpl adapters, implements domain repository interfaces using JPA repositories, adds entity mappers (ProductEntityMapper, CategoryEntityMapper), configures proper domain-to-entity conversion with ID handling, adds ProductProjectionMapper for search projections. ([5c47088](https://github.com/oliver-Victorxavier/product-catalog/commit/5c47088b1cc8da03c79fa56035236b113b135206))
* add domain entities and interfaces for JWT authentication ([51cea7b](https://github.com/oliver-Victorxavier/product-catalog/commit/51cea7be57ed89e0e597f7143512355c197f98f0))
* add mappers, validators and update controllers for JWT auth ([ade8aa8](https://github.com/oliver-Victorxavier/product-catalog/commit/ade8aa8b45cea559b1e56011e671d6b383803e12))
* add professional JWT dependencies (jjwt) ([f372f0f](https://github.com/oliver-Victorxavier/product-catalog/commit/f372f0f3798bac1d52b32cb02c7bf0049b7ed4a0))
* adds security configuration and exception handling ([6fce1bf](https://github.com/oliver-Victorxavier/product-catalog/commit/6fce1bfc07370e699b3f400846c8df740d6799c6))
* **application:** implements service layer and exception handling — Adds application and infrastructure layers to complete the implementation of Clean Architecture: Creates service implementations for Product and Category use cases, implements DTO mappers for domain object conversion, adds exception handling for database scenarios and resources not found, refines repository interfaces with consistent method signatures. ([9d599e8](https://github.com/oliver-Victorxavier/product-catalog/commit/9d599e8a7e0cb14d727b3078825d249ff548ba55))
* configure security settings and dependency injection ([a8fadda](https://github.com/oliver-Victorxavier/product-catalog/commit/a8faddafe2e5a45fb9680b9359a02aed26f7cb5e))
* **controllers:** implements REST API controllers ([e2e2def](https://github.com/oliver-Victorxavier/product-catalog/commit/e2e2def8a49dcf95aa7cb7615191619be5634352))
* **core:** initialize product catalog domain model — Implement core domain entities and interfaces following Clean Architecture principles: Create Product and Category domain entities, define repository interfaces for data access, implement DTOs for data transfer, set up pagination support, define use case interfaces for business operations. This commit establishes the foundation for the product catalog system with a clear separation of concerns and domain-driven design approach. ([ef0d6f6](https://github.com/oliver-Victorxavier/product-catalog/commit/ef0d6f6d4f39a9b09b572712f143b675cb1aebe1))
* **exceptions:** implement comprehensive exception handling ([4690d24](https://github.com/oliver-Victorxavier/product-catalog/commit/4690d24423268f628a1690da21e0803e25c936ed))
* implement authentication use cases and user service ([e6fdb93](https://github.com/oliver-Victorxavier/product-catalog/commit/e6fdb93efc00e2b1790019a08dfbd84532eb6afe))
* implement comprehensive role-based access control system ([6c4ab60](https://github.com/oliver-Victorxavier/product-catalog/commit/6c4ab608d92a2de7e733c5e3a0f92463e2a525d6))
* implement JWT and security services with RSA keys ([700f012](https://github.com/oliver-Victorxavier/product-catalog/commit/700f0127d3b04aba6f6494e7a57337567aeb1688))
* implements authentication and user management system ([de76fc9](https://github.com/oliver-Victorxavier/product-catalog/commit/de76fc976dd6a22f3818e1a96086577a4e4d0b28))
* **jpa:** implements JPA entities and repositories — Creates ProductEntity and CategoryEntity with appropriate JPA annotations, implements many-to-many relationship through the tb_product_category join table, adds ProductJpaRepository and CategoryJpaRepository with custom queries, adds data.sql with initial product and category data. ([9bb89ea](https://github.com/oliver-Victorxavier/product-catalog/commit/9bb89ea52a485d3dc7018e0840ae3c6d1b01e6a9))
* **repositories:** completes the repository implementation layer ([0edfefb](https://github.com/oliver-Victorxavier/product-catalog/commit/0edfefb6d7f79686a2fbd6f2babbdeedb37001a3))


### 🐛 Bug Fixes

* add missing conventional-changelog-conventionalcommits dependency ([7631365](https://github.com/oliver-Victorxavier/product-catalog/commit/7631365bea0f23cbcb931e52fc009a72d59fddab))
* add missing mappers for application to work ([285fe7e](https://github.com/oliver-Victorxavier/product-catalog/commit/285fe7efb50900636df5970fedfb20bfba756404))
* add missing Role domain entity ([3299424](https://github.com/oliver-Victorxavier/product-catalog/commit/32994246ff6600cf2734be9772745be44d4c6cf7))
* correct DockerHub image name to victorxavie/product-catalog ([9282833](https://github.com/oliver-Victorxavier/product-catalog/commit/92828339529b9eeebfae1013d823d5daac04a2aa))
* correct ProductController mapping to /api/products ([0fcc6c1](https://github.com/oliver-Victorxavier/product-catalog/commit/0fcc6c1c3f825b34ffdd357ece0fd40b388c1244))
* correct repository URLs in GitHub Actions and package.json ([1c4ec75](https://github.com/oliver-Victorxavier/product-catalog/commit/1c4ec7596b1d94daf4d4040807f452183fa9e13d))


### ♻️ Code Refactoring

* improve code organization and add Docker configuration ([745e770](https://github.com/oliver-Victorxavier/product-catalog/commit/745e7701490c9a26975e42c70c5b7f092a278aa5))
* improve pagination and repository layer ([7851ec5](https://github.com/oliver-Victorxavier/product-catalog/commit/7851ec58b7a49e029749d470c42b3b1b50c22169))
* improve service and repository implementations ([fc31a66](https://github.com/oliver-Victorxavier/product-catalog/commit/fc31a66368b31a59c6d05f15dba3402827be6339))
* move mappers to the domain layer and remove Spring dependencies ([d37cc0b](https://github.com/oliver-Victorxavier/product-catalog/commit/d37cc0b1dabfbe9810d171cf6f7fbfdacd9c2c01))
* remove code duplication in ProductServiceImpl ([5723f20](https://github.com/oliver-Victorxavier/product-catalog/commit/5723f206a28aee82cd642f915c6c964eae6089b9))
