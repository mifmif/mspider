megaspider
=======
MegaSpider : a pattern based web data extractor, template maker and web site cloner. that scrap web pages and handle fields specified by the user, 
and navigate over several links in the web sites contents based on a set of patterns roles specified by the user. The scrapped data are stored in a database , JPA based data access layer.


**MegaSpider Metamodel **

MegaSpider is based on a well defined MetaModel that present in a high level the abstract presentation of a web site, it focus on the  generic composition of web sites.

**How to use it :**

Define the sitemap : your web site and URL pattern that you want to navigate to,link between them, and their fields that you want to scrap. then start the engine. example : see 
com.mifmif.networking.mspider.Main

**Features :**

-Navigate over URLs of a web site based on a set predefined URLPatterns.

-Follow links in each URL.

-Scrap payload from predefined fields of each URLPattern.

-Create template for each URLPattern.

-Structured storage (Data storage are configured from META-INF/persistance.xml).

-Compatibility with all RDBMS Data stores (Oracle,PostgresSQL,MySQL..)


**TODO :**

-For data extracted from a web site ,define dynamically class  for each DomainObjectModel used , it's Field will present the attributes of the new defined class.

-Create new database schema for the defined class and classify & store extracted data into the new database.

-auto induction of our wrapper megaspider , input will be the URL of the web site. The learning technique have to show possibilities of sitemap that could be applied (URL Patterns to use, link between them, fields to extract).

-statistical analyze of the web site before choosing the sitemap to help best choice of the write structure. 