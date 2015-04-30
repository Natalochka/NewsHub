CREATE TABLE IF NOT EXISTS Tags (
  id   INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(25) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS Articles (
  id               INT PRIMARY KEY AUTO_INCREMENT,
  title            VARCHAR(255) NOT NULL,
  content          TEXT,
  publication_date DATETIME     NOT NULL,
  published        BOOLEAN         DEFAULT FALSE,
  archive          BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS ArticlesTags (
  article_id INT NOT NULL,
  FOREIGN KEY (article_id) REFERENCES Articles (id),
  tag_id     INT NOT NULL,
  FOREIGN KEY (tag_id) REFERENCES Tags (id),
  PRIMARY KEY (article_id, tag_id)
);

CREATE TABLE IF NOT EXISTS MainArticles (
  main_article_id INT PRIMARY KEY,
  article_id      INT NOT NULL UNIQUE,
  FOREIGN KEY (article_id) REFERENCES Articles (id),
  featured        BOOLEAN
);

CREATE TABLE IF NOT EXISTS Privileges (
  id             INT PRIMARY KEY AUTO_INCREMENT,
  name           VARCHAR(25) NOT NULL UNIQUE,
  addArticle BOOLEAN NOT NULL,
  addArticleToMain BOOLEAN NOT NULL,
  removeArticleFromMain BOOLEAN NOT NULL,
  editArticle BOOLEAN NOT NULL,
  publishArticle BOOLEAN NOT NULL,
  archiveArticle BOOLEAN NOT NULL,
  featureArticle BOOLEAN NOT NULL,
  deleteArticle BOOLEAN NOT NULL,
  getArticle BOOLEAN NOT NULL,
  getMainArticle BOOLEAN NOT NULL,
  addTag BOOLEAN NOT NULL,
  editTag BOOLEAN NOT NULL,
  deleteTag BOOLEAN NOT NULL,
  getTag BOOLEAN NOT NULL,
  addUser BOOLEAN NOT NULL,
  changeUserPrivileges BOOLEAN NOT NULL,
  deleteUser BOOLEAN NOT NULL,
  editUserInfo BOOLEAN NOT NULL,
  getUser BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS Users (
  id           INT PRIMARY KEY AUTO_INCREMENT,
  login        VARCHAR(50) NOT NULL UNIQUE,
  password     VARCHAR(36) NOT NULL,
  email        VARCHAR(50) NOT NULL,
  first_name   VARCHAR(25) NOT NULL,
  last_name    VARCHAR(25) NOT NULL,
  privilege_id INT         NOT NULL,
  FOREIGN KEY (privilege_id) REFERENCES Privileges (id)
);