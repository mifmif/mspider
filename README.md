MegaSpider
=======
MegaSpider : a pattern based web data extractor, template maker and web site cloner. that scrap web pages and handle fields specified by the user, 
and navigate over several links in the web sites contents based on a set of patterns roles specified by the user. The scrapped data are stored in a database , JPA based data access layer.


**MegaSpider Metamodel :**
-

MegaSpider is based on a well defined MetaModel that present in a high level the abstract presentation of a web site, it focus on the  generic composition of web sites.
![MegaSpider Class Diagram](MSpider/Mspider Model.png?raw=true "MegaSpider Class Diagram")
**How to use it :**
-

Define the sitemap : your web site and URL pattern that you want to navigate to,link between them, and their fields that you want to scrap. then start the engine. 
**example :**
In this example we bootstrap Database by storing 'http://www.stackoverflow.com' sitemap as a sample ,  we just focus on question/answer/vote/comment/tag fields .

```java

		// Create website instance
		String host = "http://www.stackoverflow.com";
		Website website = new Website(host);

		// Create urls pattern
		URLPattern questionsHome = URLPattern.builder().withWebsite(website).withUrlPattern("/questions").withUrlName("HomePage").withContentStatic(false)
				.build();
		URLPattern nextQuestionsPage = URLPattern.builder().withWebsite(website).withUrlPattern("/questions\\?page=[0-9]+&sort=newest").withUrlName("NextPage")
				.withContentStatic(false).build();
		URLPattern questionPage = URLPattern.builder().withWebsite(website).withUrlPattern("/questions/[0-9]+/.+").withUrlName("QuestionPage")
				.withContentStatic(true).build();
		URLPattern excludedPat1 = URLPattern.builder().withUrlPattern("/questions\\?page=[0-9]{3,}&sort=newest").withExcludeFrom(website).build();

		// questionsHome mapping links to others pattern pages
		questionsHome.getNextUrls().add(questionPage);
		questionsHome.getNextUrls().add(nextQuestionsPage);

		// nextQuestionsPage mapping links to others pattern pages
		nextQuestionsPage.getNextUrls().add(questionPage);
		nextQuestionsPage.getNextUrls().add(nextQuestionsPage);

		// Create a domain object model
		DomainObjectModel questionModel = new DomainObjectModel(website, "Question");

		// Create fields
		Field titleField = new Field(questionModel, questionPage, QUESTION_TITLE_CSS_SELECTOR, "Title");
		Field questionField = new Field(questionModel, questionPage, QUESTION_CSS_SELECTOR, QUESTION_BODY_CSS_SELECTOR, "Question");
		Field answerField = new Field(questionModel, questionPage, ANSWER_CSS_SELECTOR, ANSWER_BODY_CSS_SELECTOR, "Answer");
		Field commentOfAnswerField = new Field(questionModel, questionPage, ANSWER_COMMENT_CSS_SELECTOR, "CommentOfAnswer");
		Field voteOfAnswerField = new Field(questionModel, questionPage, ANSWER_VOTE_CSS_SELECTOR, "VoteOfAnswer");
		Field isAcceptedAnswerField = new Field(questionModel, questionPage, ANSWER_IS_ACCEPTED_CSS_SELECTOR, "isAcceptedOfAnswer");
		Field tagsField = new Field(questionModel, questionPage, TAG_CSS_SELECTOR, "Tags");
		answerField.setManyElements(true);
		commentOfAnswerField.setManyElements(true);
		tagsField.setManyElements(true);
		tagsField.setSelectorForParentElement(true);

		// attach field to the domain model
		questionModel.getFields().add(titleField);
		questionModel.getFields().add(questionField);
		questionModel.getFields().add(tagsField);
		questionModel.getFields().add(answerField);// we didn't add
													// commentOfAnswerField and
													// isAcceptedAnswerField and
													// voteOfAnswerField because
													// they belong to the field
													// answer

		// attach subfields to their parent
		commentOfAnswerField.setParentField(answerField);
		answerField.getSubFields().add(commentOfAnswerField);

		voteOfAnswerField.setParentField(answerField);
		answerField.getSubFields().add(voteOfAnswerField);

		isAcceptedAnswerField.setParentField(answerField);
		answerField.getSubFields().add(isAcceptedAnswerField);

		// Add fields to urls pattern
		questionPage.getFields().add(titleField);
		questionPage.getFields().add(tagsField);
		questionPage.getFields().add(questionField);
		questionPage.getFields().add(answerField);

		// add urls to the web site
		website.setStartPage(questionsHome.getUrlPattern());
		website.getPatterns().add(questionsHome);
		website.getPatterns().add(nextQuestionsPage);
		website.getPatterns().add(questionPage);

		// add excluded urls to the web site
		website.getExcludedPatterns().add(excludedPat1);

		// add Domain object model to the web site
		website.getObjectModels().add(questionModel);

		// save website
		websiteDao.persist(website);
		
		//Create engine and start data extraction
		Engine engine = new Engine(website);
		engine.start(); 
```

**Features :**
-

-Navigate over URLs of a web site based on a set predefined URLPatterns.

-Follow links in each URL.

-Scrap payload from predefined fields of each URLPattern.

-Create template for each URLPattern.

-Regex to String generation : build value of parameters from regular expression and use them in requests. 

-Structured storage (Data storage is configured from META-INF/persistance.xml).

-Compatibility with all RDBMS Data stores (Oracle,PostgresSQL,MySQL..)


**TODO :**
-


-For data extracted from a web site ,define dynamically class  for each DomainObjectModel used , it's Field will present the attributes of the new defined class.

-Create new database schema for the defined classes and classify & store payload into the new database.

-auto induction of our wrapper megaspider , input will be the URL of the web site. The learning technique have to show possibilities of sitemap that could be applied (URL Patterns to use, link between them, fields to extract).

-statistical analyze of the web site before choosing the sitemap to help user to choose better choice that result best data classification. 
