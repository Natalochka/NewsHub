CREATE TABLE Articles (
  id               INT PRIMARY KEY AUTO_INCREMENT,
  title            VARCHAR(255) NOT NULL,
  content          TEXT,
  checked          BOOLEAN         DEFAULT FALSE,
  featured         BOOLEAN         DEFAULT FALSE,
  approved         BOOLEAN         DEFAULT FALSE,
  archived         BOOLEAN         DEFAULT FALSE,
  draft            BOOLEAN         DEFAULT FALSE,
  rejected         BOOLEAN         DEFAULT FALSE,
  image            TEXT,
  number_on_main   INT UNIQUE,
  publication_date DATETIME     NOT NULL
);

CREATE TABLE IF NOT EXISTS Tags (
  id   INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(25) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS ArticlesTags (
  article_id INT NOT NULL,
  FOREIGN KEY (article_id) REFERENCES Articles (id),
  tag_id     INT NOT NULL,
  FOREIGN KEY (tag_id) REFERENCES Tags (id),
  PRIMARY KEY (article_id, tag_id)
);

CREATE TABLE Privileges (
  id                    INT PRIMARY KEY AUTO_INCREMENT,
  name                  VARCHAR(25) NOT NULL UNIQUE,
  addArticle            BOOLEAN     NOT NULL,
  addArticleToMain      BOOLEAN     NOT NULL,
  removeArticleFromMain BOOLEAN     NOT NULL,
  editArticle           BOOLEAN     NOT NULL,
  checkArticle          BOOLEAN     NOT NULL,
  approveArticle        BOOLEAN     NOT NULL,
  archiveArticle        BOOLEAN     NOT NULL,
  featureArticle        BOOLEAN     NOT NULL,
  deleteArticle         BOOLEAN     NOT NULL,
  draftArticle          BOOLEAN     NOT NULL,
  rejectArticle         BOOLEAN     NOT NULL,
  setImage              BOOLEAN     NOT NULL,
  getArticle            BOOLEAN     NOT NULL,
  getAllArticles        BOOLEAN     NOT NULL,
  getArticlesByTagId    BOOLEAN     NOT NULL,
  getArticlesTags       BOOLEAN     NOT NULL,
  addTag                BOOLEAN     NOT NULL,
  addTagToArticle       BOOLEAN     NOT NULL,
  editTag               BOOLEAN     NOT NULL,
  deleteTag             BOOLEAN     NOT NULL,
  getTag                BOOLEAN     NOT NULL,
  getAllTags            BOOLEAN     NOT NULL,
  getTagsByArticleId    BOOLEAN     NOT NULL,
  getUserByArticleId    BOOLEAN     NOT NULL,
  addUser               BOOLEAN     NOT NULL,
  changeUserPrivileges  BOOLEAN     NOT NULL,
  deleteUser            BOOLEAN     NOT NULL,
  editUserInfo          BOOLEAN     NOT NULL,
  getUser               BOOLEAN     NOT NULL,
  getAllUsers           BOOLEAN     NOT NULL
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

CREATE TABLE IF NOT EXISTS UsersArticles (
  user_id    INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES Users (id),
  article_id INT NOT NULL,
  FOREIGN KEY (article_id) REFERENCES Articles (id),
  PRIMARY KEY (user_id, article_id)
);