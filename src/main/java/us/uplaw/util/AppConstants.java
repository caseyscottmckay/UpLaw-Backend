package us.uplaw.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public interface AppConstants {

  String DEFAULT_PAGE_NUMBER = "0";

  String DEFAULT_PAGE_SIZE = "30";

  int MAX_PAGE_SIZE = 50;

  static final String ELASTICSEARCH_INDEX = "document";

  static final String ELASTICSEARCH_DOCUMENT_TYPE = "document";

  static final int ELASTICSEARCH_PORT = 9300;

  public static String uplawDataDirectroy = "/data/uplaw/";

  public static String getElasticsearchHost() {
    return "35.243.154.32";
  }//35.196.127.211

  public static int getElasticSearchBulkIndexDocumentLimit() {
    return 500;
  }

  public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

  public static final String SQLITE_JDBC_DRIVER = "org.sqlite.JDBC";

  public static final String PG_JDBC_DRIVER = "org.postgresql.Driver";
  public static final String DATABASE_URL = "jdbc:mysql://localhost:3306/uplaw";

  public static final String DATABASE_USER = "uplaw";

  public static final String DATABASE_PASSWORD = "uplaw";

  public final String SQLITE_URL_TEMPLATE = "jdbc:sqlite:%s";

  public final String PG_URL_TEMPLATE = "jdbc:postgresql://10.0.0.104:5432/%s";

  public final int SQLITE_CACHE_SIZE = 20000;

  static final Duration EXPIRES_AFTER = Duration.ofDays(7L);

  static final Pattern COURTLISTENER_API_PATH = Pattern.compile("^/api/rest/v3/\\w+/(\\d+)/?$", Pattern.CASE_INSENSITIVE);

  static final String CACHE_DIRECTORY = "/home/casey/data/cache";

  static final String DATA_DIRECTORY = "/home/casey/data/data";

  static final String DUMP_DIRECTORY = "/home/casey/data/dump";

  static final String COURTLISTENER_DOMAIN_NAME = "courtlistener";

  static final  String ISO_8601_24H_FULL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX";


  static final String COURTLISTENER_URL = "https://www.courtlistener.com/api/bulk-data/";


  static String COURT_LISTENER_JURISDICTIONS_ABBREVIATED() {
    return "almb,alsb,tned,lawb";
    //return "scotus,ca1,ca2,ca3,ca4,ca5,ca6,ca7,ca8,ca9,ca10,ca11";
    /*return "acca,afcca,ag,akb,akd,ala,alacivapp,alacrimapp,alactapp,alaska,alaskactapp,ald,almb,almd,alnb,alnd,alsb,alsd,arb,areb,ared,ariz,arizctapp,arizsuper,ariztaxct,ark,arkag,arkctapp,arkworkcompcom,armfor,arwb,arwd,asbca,azd,bap1,bap2,bap6,bap8,bap9,bap10,bapma,bapme,bva,ca1,ca2,ca3,ca4,ca5,ca6,ca7,ca8,ca9,ca10,ca11,caca,cacb,cacd,cadc,caeb,caed,cafc,cal,calag,calappdeptsuper,calctapp,californiad,canalzoned,canb,cand,casb,casd,cavc,cc,ccpa,circtdel,circtnc,circttenn,cit,cjdpa,cob,cod,colo,coloag,coloctapp,coloworkcompcom,com,conn,connappct,connsuperct,connworkcompcom,ctb,ctd,cusc,dc,dcb,dcd,deb,ded,del,delch,delctcompl,delfamct,deljudct,delsuperct,eca,fisc,fiscr,fla,flaag,fladistctapp,fld,flmb,flmd,flnb,flnd,flsb,flsd,ga,gactapp,gad,gamb,gamd,ganb,gand,gasb,gasd,gub,gud,haw,hawapp,hib,hid,iad,ianb,iand,iasb,iasd,idaho,idahoctapp,idb,idd,ilcb,ilcd,ill,illappct,illinoisd,illinoised,ilnb,ilnd,ilsb,ilsd,ind,indctapp,indianad,indtc,innb,innd,insb,insd,iowa,iowactapp,jpml,kan,kanag,kanctapp,kingsbench,ksb,ksd,ky,kyctapp,kyctapphigh,kyd,kyeb,kyed,kywb,kywd,la,laag,lactapp,lad,laeb,laed,lamb,lamd,lawb,lawd,mab,mad,mass,massappct,massdistct,masssuperct,maworkcompcom,mc,md,mdag,mdb,mdctspecapp,mdd,me,meb,med,mich,michctapp,michd,mieb,mied,minn,minnag,minnctapp,miss,missctapp,missd,miwb,miwd,mnb,mnd,mo,moag,moctapp,mod,moeb,moed,mont,montag,monttc,mowb,mowd,msnb,msnd,mspb,mssb,mssd,mtb,mtd,nc,ncctapp,ncd,nceb,nced,ncmb,ncmd,ncsuperct,ncwb,ncwd,ncworkcompcom,nd,ndb,ndctapp,ndd,neb,nebag,nebctapp,nebraskab,ned,nev,nh,nhb,nhd,nj,njb,njch,njd,njsuperctappdiv,njtaxct,nm,nmb,nmcca,nmctapp,nmd,nmib,nmid,nvb,nvd,ny,nyag,nyappdiv,nyappterm,nycivct,nycrimct,nyd,nyeb,nyed,nyfamct,nynb,nynd,nysb,nysd,nysupct,nysurct,nywb,nywd,ohio,ohioctapp,ohioctcl,ohiod,ohnb,ohnd,ohsb,ohsd,okeb,oked,okla,oklaag,oklacivapp,oklacoj,oklacrimapp,oklajeap,oknb,oknd,okwb,okwd,or,orb,orctapp,ord,orld,ortc,pa,pacommwct,paeb,paed,pamb,pamd,pasuperct,pawb,pawd,pennsylvaniad,prb,prd,psc,reglrailreorgct,ri,rib,rid,risuperct,sc,scb,scctapp,scd,scotus,sd,sdb,sdd,southcarolinaed,southcarolinawd,stp,sttex,tax,tecoa,tenn,tenncrimapp,tennctapp,tennessed,tennesseeb,tennsuperct,test,tex,texag,texapp,texcrimapp,texd,texjpml,texreview,tneb,tned,tnmb,tnmd,tnwb,tnwd,txeb,txed,txnb,txnd,txsb,txsd,txwb,txwd,uscfc,usjc,utah,utahctapp,utb,utd,va,vactapp,vad,vaeb,vaed,vawb,vawd,vib,vid,vt,vtb,vtd,vtsuperct,waeb,waed,wash,washag,washctapp,washd,wawb,wawd,wieb,wied,wis,wisag,wisctapp,wisd,wiwb,wiwd,wva,wvad,wvnb,wvnd,wvsb,wvsd,wyb,wyd,wyo";*/
  }

  public static HashMap<String, String> getCourtListenerJurisdicitonMeta() throws IOException {
    String content = new String ( Files.readAllBytes( Paths.get("/home/casey/up/uplaw-backend/src/main/java/us/uplaw/util/contants/courtlistener-jurisdictions.csv") ) );

    String[] lines = content.split("\n");
    HashMap<String, String> jurisdictions = new HashMap<>();
    for(String line : lines){
      String[] parts = line.split(",");
      jurisdictions.put(parts[4],line);
    }
    return jurisdictions;
  }

  public static String CATEGORIES_AND_SUB_CATEGORIES() {
    String catsAndSubcats = "#antitrust,antitrust compliance,antitrust litigation ,cartels,general antitrust,horizontal agreements,merger control ,unilateral conduct,vertical agreements,#bankruptcy,chapter 11 commencing a case,chapter 11 case administration ,chapter 11 exiting a case,bankruptcy committees,bankruptcy litigation,creditor and inter-creditor issues in bankruptcy ,purchasing distressed assets and claims in bankruptcy,general bankruptcy,#capital markets and corporate governance,broker-dealer,corporate governance continuous disclosure,non-us issuers registered offerings,market regulation,unregistered offerings,#commercial,advertising and marketing,general contract and boilerplate,bribery corruption	,insurance claims coverage,confidentiality	joint ventures,e-commerce	,outsourcing,general commercial	,supply of goods services,#corporate,broker-dealer,corporate governance continuous disclosure,non-us issuers registered offerings,market regulation,unregistered offerings,#employee benefits and executive compensation,executive compensation,retirement plans health welfare plans,equity compensation,employee benefit plans administration compliance,employee benefit plans the employment relationship,#estate planning,charitable trusts,estate and gift tax ,general estate planning ,health care directives ,irrevocable life insurance trusts,powers of attorney for finances,revocable trusts ,supplemental needs trusts,wills,#finance,acquisition finance,asset-based lending ,bankruptcy in finance ,debt ,capital markets ,guaranties ,islamic finance,lending general,project finance development,real estate finance regulatory ,finance securitization ,structured finance security,swaps derivatives,#intellectual property and technology,copyright,general ip,information technology ,internet,ip it in corporate transactions,patent counseling transactions,patent litigation,privacy data security ,right of publicity ,trademarks,trade secrets,#international arbitration,arbitral awards challenges,arbitration agreements ,arbitrators appointments ,commencing an arbitration ,conflicts of law issues ,costs funding,enforcement,institutional ad-hoc arbitration,interim measures ,investment treaty arbitration ,jurisdictional issues ,multiparty arbitration ,procedure evidence,remedies,#labor and employment,contracts,leave law,corporate transactions,bankruptcy,policies and procedures,discipline,internal investigations,recruitment hiring,discrimination ,harassment,retaliation,restrictive covenants confidentiality ip ,employee data monitoring privacy	,termination layoffs plant closings,employment litigation	,unions,health safety	,wage hour law,immigration,#litigation,commencement pleadings,removal,discovery evidence,motions,trial post-trial,judgment,appeals,general litigation,arbitration,class actions,antitrust litigation,employment litigation,intellectual property litigation,securities litigation and enforcement,other specialist litigation,#real estate,commercial finance,commercial leasing ,commercial ownership,residential finance,residential leasing,residential ownership ,construction,real estate,corporate transactions";
    return catsAndSubcats;
  }

  public static String PRACTICE_AREAS() {
    String catout = "";
    String cats = "Admiralty and Maritime,Antitrust,Bankruptcy,Capital Markets,Commercial Law,Construction Law,Corporate Counsel,Corporate Governance,Corporations,Criminal Law,Data Privacy,Employee Benefits and Executive Compensation,Employment,Energy and Environment,Estate Planning,Family Law,Finance and Banking,Government Contracts,Health Law,Immigration,Insurance Law,Intellectual Property,Medical Litigation,Mergers and Acquisitions,Military Law,Municipal Law,Native American Law,Pension and Retirement Benefits,Products Liability,Real Property,Securities Enforcement and Litigation,Tax,Workers Compensation";
    return cats;
  }

  public static String KEY_TOPICS() {
    String keyTopics = "abandoned and lost property,abatement and revival,abortion and birth control,absentees,abstracts of title,accession,accord and satisfaction,account,account stated,accountants,acknowledgment,action,action on the case,adjoining landowners,administrative law and procedure,admiralty,adoption,adulteration,adverse possession,affidavits,agriculture,alteration of instruments,alternative dispute resolution,ambassadors and consuls,amicus curiae,animals,annuities,antitrust and trade regulation,appeal and error,appearance,armed services,arrest,arson,assault and battery,assignments,associations,asylums and assisted living facilities,attachment,attorney and client,attorney general,auctions and auctioneers,audita querela,automobiles,aviation,bail,bailment,bankruptcy,banks and banking,beneficial associations,bills and notes,bonds,boundaries,bounties,bribery,bridges,brokers,building and loan associations,burglary,cancellation of instruments,carriers,cemeteries,census,certiorari,champerty and maintenance,charities,chattel mortgages,chemical dependents,child custody,child support,civil rights,clerks of courts,clubs,collision,commerce,commodity futures trading regulation,common interest communities,common lands,common law,compromise and settlement,confusion of goods,conspiracy,constitutional law,consumer credit,contempt,contracts,contribution,controlled substances,conversion and civil theft,convicts,copyrights and intellectual property,coroners,corporations and business organizations,costs,counterfeiting,counties,court commissioners,courts,covenants,credit reporting agencies,criminal law,crops,currency regulation,customs and usages,customs duties,damages,dead bodies,death,debtor and creditor,declaratory judgment,dedication,deeds,deposits and escrows,deposits in court,descent and distribution,detectives and security guards,detinue,disorderly conduct,disorderly house,district and prosecuting attorneys,district of columbia,disturbance of public assemblage,divorce,domicile,double jeopardy,easements,education,ejectment,election law,election of remedies,electricity,embezzlement,eminent domain,environmental law,equitable conversion,equity,escape,escheat,estates in property,estoppel,evidence,exchange of property,exchanges,execution,executors and administrators,exemptions,explosives,extortion,extradition and detainers,factors,false imprisonment,false personation,false pretenses,federal civil procedure,federal courts,fences,ferries,fines,fires,fish,fixtures,food,forcible entry and detainer,forfeitures,forgery,franchises,fraud,fraudulent conveyances,game,gaming and lotteries,garnishment,gas,gifts,good will,grand jury,guaranty,guardian and ward,habeas corpus,hawkers and peddlers,health,highways,holidays,homestead,homicide,implied and constructive contracts,improvements,incest,indemnity,indians,indictment and information,infants,injunction,innkeepers,inspection,insurance,insurrection and sedition,interest,internal revenue,international law,interpleader,intoxicating liquors,joint tenancy,joint ventures,judges,judgment,judicial sales,jury,justices of the peace,kidnapping,labor and employment,landlord and tenant,larceny,libel and slander,licenses,liens,life estates,limitation of actions,lis pendens,lobbying,logs and logging,lost instruments,malicious mischief,malicious prosecution,mandamus,manufactures,maritime liens,marriage and cohabitation,mayhem,mental health,military justice,militia,mines and minerals,monopolies,mortgages and deeds of trust,motions,municipal corporations,names,ne exeat,negligence,neutrality laws,newspapers,new trial,notaries,notice,novation,nuisance,oath,obscenity,obstructing justice,pardon and parole,parent and child,parliamentary law,parties,partition,partnership,party walls,patents,payment,penalties,pensions,perjury,perpetuities,pilots,pleading,pledges,possessory warrant,postal service,powers,pretrial procedure,principal and agent,principal and surety,prisons,private roads,privileged communications and confidentiality,process,products liability,prohibition,property,prostitution,protection of endangered persons,public amusement and entertainment,public assistance,public contracts,public employment,public lands,public utilities,quieting title,quo warranto,racketeer influenced and corrupt organizations,railroads,real actions,receivers,receiving stolen goods,recognizances,records,reference,reformation of instruments,registers of deeds,release,religious societies,remainders,removal of cases,replevin,reports,rescue,reversions,review,rewards,riot,robbery,sales,salvage,scire facias,seals,seamen,searches and seizures,secured transactions,securities regulation,seduction,sentencing and punishment,sequestration,sex offenses,sheriffs and constables,shipping,signatures,slaves,social security,specific performance,spendthrifts,states,statutes,steam,stipulations,submission of controversy,subrogation,subscriptions,suicide,sunday,supersedeas,taxation,telecommunications,tenancy in common,tender,territories,time,torts,towage,towns,trademarks,treason,treaties,trespass,trespass to try title,trial,trusts,turnpikes and toll roads,undertakings,unemployment compensation,united states,united states magistrate judges,united states marshals,unlawful assembly,urban railroads,usury,vagrancy,vendor and purchaser,venue,war and national emergency,warehousemen,waste,water law,weapons,weights and measures,wharves,wills,witnesses,woods and forests,zoning and planning";
    return keyTopics;
  }

  public static String abbreviateJurisdiction(String inputCountryNameCamelCase) {
    HashMap<String, String> countriesAbbreviationAndCode = new HashMap<>();
    int i = 0;
    String abbreviationOut = "";
    String countyNamesAndAbreviations = "Afghanistan#AF+Aland Islands#AX+Albania#AL+Algeria#DZ+American Samoa#AS+Andorra#AD+Angola#AO+Anguilla#AI+Antarctica#AQ+Antigua and Barbuda#AG+Argentina#AR+Armenia#AM+Aruba#AW+Australia#AU+Austria#AT+Azerbaijan#AZ+Bahamas#BS+Bahrain#BH+Bangladesh#BD+Barbados#BB+Belarus#BY+Belgium#BE+Belize#BZ+Benin#BJ+Bermuda#BM+Bhutan#BT+Bolivia#BO+Bosnia and Herzegovina#BA+Botswana#BW+Bouvet Island#BV+Brazil#BR+British Virgin Islands#VG+British Indian Ocean Territory#IO+Brunei Darussalam#BN+Bulgaria#BG+Burkina Faso#BF+Burundi#BI+Cambodia#KH+Cameroon#CM+Canada#CA+CAN#CA+Cayman Islands#KY+Central African Republic#CF+Chad#TD+Chile#CL+China#CN+Hong Kong#HK+Macao,SAR China#MO+Christmas Island#CX+Cocos (Keeling) Islands#CC+Colombia#CO+Comoros#KM+Congo (Brazzaville)#CG+Congo,(Kinshasa)#CD+Cook Islands#CK+Costa Rica#CR+Côte d'Ivoire#CI+Croatia#HR+Cuba#CU+Cyprus#CY+Czech Republic#CZ+Denmark#DK+Djibouti#DJ+Dominica#DM+Dominican Republic#DO+Ecuador#EC+Egypt#EG+El Salvador#SV+Equatorial Guinea#GQ+Eritrea#ER+Estonia#EE+Ethiopia#ET+Falkland Islands (Malvinas)#FK+Faroe Islands#FO+Fiji#FJ+Finland#FI+France#FR+French Guiana#GF+French Polynesia#PF+French Southern Territories#TF+Gabon#GA+Gambia#GM+Georgia#GE+Germany#DE+Ghana#GH+Gibraltar#GI+Greece#GR+Greenland#GL+Grenada#GD+Guadeloupe#GP+Guam#GU+Guatemala#GT+Guernsey#GG+Guinea#GN+Guinea-Bissau#GW+Guyana#GY+Haiti#HT+Heard and Mcdonald Islands#HM+Holy See (Vatican City State)#VA+Honduras#HN+Hungary#HU+Iceland#IS+India#IN+Indonesia#ID+Iran,Islamic Republic of#IR+Iraq#IQ+Ireland#IE+Isle of Man#IM+Israel#IL+Italy#IT+Jamaica#JM+Japan#JP+Jersey#JE+Jordan#JO+Kazakhstan#KZ+Kenya#KE+Kiribati#KI+Korea (North)#KP+Korea (South)#KR+Kuwait#KW+Kyrgyzstan#KG+Lao PDR#LA+Latvia#LV+Lebanon#LB+Lesotho#LS+Liberia#LR+Libya#LY+Liechtenstein#LI+Lithuania#LT+Luxembourg#LU+Macedonia,Republic of#MK+Madagascar#MG+Malawi#MW+Malaysia#MY+Maldives#MV+Mali#ML+Malta#MT+Marshall Islands#MH+Martinique#MQ+Mauritania#MR+Mauritius#MU+Mayotte#YT+Mexico#MX+Micronesia,Federated States of#FM+Moldova#MD+Monaco#MC+Mongolia#MN+Montenegro#ME+Montserrat#MS+Morocco#MA+Mozambique#MZ+Myanmar#MM+Namibia#NA+Nauru#NR+Nepal#NP+Netherlands#NL+Netherlands Antilles#AN+New Caledonia#NC+New Zealand#NZ+Nicaragua#NI+Niger#NE+Nigeria#NG+Niue#NU+Norfolk Island#NF+Northern Mariana Islands#MP+Norway#NO+Oman#OM+Pakistan#PK+Palau#PW+Palestinian Territory#PS+Panama#PA+Papua New Guinea#PG+Paraguay#PY+Peru#PE+Philippines#PH+Pitcairn#PN+Poland#PL+Portugal#PT+Puerto Rico#PR+Qatar#QA+Réunion#RE+Romania#RO+Russian Federation#RU+Rwanda#RW+Saint-Barthélemy#BL+Saint Helena#SH+Saint Kitts and Nevis#KN+Saint Lucia#LC+Saint-Martin (French part)#MF+Saint Pierre and Miquelon#PM+Saint Vincent and Grenadines#VC+Samoa#WS+San Marino#SM+Sao Tome and Principe#ST+Saudi Arabia#SA+Senegal#SN+Serbia#RS+Seychelles#SC+Sierra Leone#SL+Singapore#SG+Slovakia#SK+Slovenia#SI+Solomon Islands#SB+Somalia#SO+South Africa#ZA+South Georgia and the South Sandwich Islands#GS+South Sudan#SS+Spain#ES+Sri Lanka#LK+Sudan#SD+Suriname#SR+Svalbard and Jan Mayen Islands#SJ+Swaziland#SZ+Sweden#SE+Switzerland#CH+Syrian Arab Republic (Syria)#SY+Taiwan,Republic of China#TW+Taiwan#TW+Tajikistan#TJ+Tanzania,United Republic of#TZ+Thailand#TH+Timor-Leste#TL+Togo#TG+Tokelau#TK+Tonga#TO+Trinidad and Tobago#TT+Tunisia#TN+Turkey#TR+Turkmenistan#TM+Turks and Caicos Islands#TC+Tuvalu#TV+Uganda#UG+Ukraine#UA+United Arab Emirates#AE+United Kingdom#GB+UK#GB+United States#US+United States of America#US+US Minor Outlying Islands#UM+Uruguay#UY+Uzbekistan#UZ+Vanuatu#VU+Venezuela (Bolivarian Republic)#VE+Viet Nam#VN+Virgin Islands,US#VI+Wallis and Futuna Islands#WF+Western Sahara#EH+Yemen#YE+Zambia#ZM";
    Map<String, String> mapCountries = new HashMap<>();//Splitter.on( "+" ).withKeyValueSeparator( '#' ).split( countyNamesAndAbreviations );
    if (mapCountries.containsValue(inputCountryNameCamelCase)) {
      abbreviationOut = mapCountries.get(inputCountryNameCamelCase);
    }
    String stateNamesAndAbbreviations = "Alabama#AL+Alaska#AK+Arizona#AZ+Arkansas#AR+California#CA+Colorado#CO+Connecticut#CT+Delaware#DE+District Of Columbia#DC+Florida#FL+Georgia#GA+Hawaii#HI+Idaho#ID+Illinois#IL+Indiana#IN+Iowa#IA+Kansas#KS+Kentucky#KY+Louisiana#LA+Maine#ME+Maryland#MD+Massachusetts#MA+Michigan#MI+Minnesota#MN+Mississippi#MS+Missouri#MO+Montana#MT+Nebraska#NE+Nevada#NV+New Hampshire#NH+New Jersey#NJ+New Mexico#NM+New York#NY+North Carolina#NC+North Dakota#ND+Ohio#OH+Oklahoma#OK+Oregon#OR+Pennsylvania#PA+Rhode Island#RI+South Carolina#SC+South Dakota#SD+Tennessee#TN+Texas#TX+Utah#UT+Vermont#VT+Virginia#VA+Washington#WA+West Virginia#WV+Wisconsin#WI+Wyoming#WY";

    Map<String, String> mapStates = new HashMap<>();//Splitter.on( "+" ).withKeyValueSeparator( '#' ).split( stateNamesAndAbbreviations );

    if (mapStates.containsValue(inputCountryNameCamelCase)) {
      abbreviationOut = mapStates.get(inputCountryNameCamelCase);
    }
    return abbreviationOut;
  }



  public static String PUBLIC_LAW_FEDERAL_JURISDICTIONS() {
    return "federal_rules_of_civil_procedure,united_states_court_of_appeals_for_the_second_circuit,first_circuit_bankruptcy_appellate_rules,supreme_court_of_the_united_states,united_states_court_of_appeals_for_the_eleventh_circuit,united_states_bankruptcy_court,united_states_court_of_appeals_for_the_fourth_circuit,trademark_trial_and_appeal_board,united_states_district_court,federal_rules_of_evidence,federal_rules_of_bankruptcy_procedure,supreme_court_of_the_united_states_rules,united_states_board_of_immigration_appeals,tenth_circuit_bankruptcy_appellate_rules,constitution_of_the_united_states,sixth_circuit_bankruptcy_appellate_rules,united_states_court_of_appeals_for_the_first_circuit,federal_rules_of_appellate_procedure,united_states_court_of_appeals_for_the_sixth_circuit,ninth_circuit_bankruptcy_appellate_rules,united_states_court_of_appeals_for_the_ninth_circuit,eighth_circuit_bankruptcy_appellate_rules,united_states_code,united_states_court_of_appeals_for_the_eighth_circuit,united_states_court_of_appeals_for_the_third_circuit,united_states_court_of_appeals_for_the_district_of_columbia_circuit,united_states_court_of_appeals_for_the_seventh_circuit,united_states_court_of_appeals_for_the_fifth_circuit,united_states_judicial_panel_on_multidistrict_litigation_rules,united_states_tax_court,internal_revenue_service_revenue_rulings,federal_rules_of_criminal_procedure,federal_sentencing_guidelines,united_states_court_of_appeals_for_the_federal_circuit,united_states_court_of_appeals_for_the_tenth_circuit";
  }

}
