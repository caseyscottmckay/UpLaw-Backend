package us.uplaw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import us.uplaw.model.Role;
import us.uplaw.model.RoleName;
import us.uplaw.repository.PollRepository;
import us.uplaw.repository.RoleRepository;
import us.uplaw.repository.UserRepository;
import us.uplaw.service.PollService;

@Configuration
public class ApplicationConfig {

  //@Autowired
  //DocumentService documentService;

  @Autowired
  PollService pollService;

  @Autowired
  PollRepository pollRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  UserRepository userRepository;

  @Bean
  CommandLineRunner runner() {
    return args -> {
      initRolls();
      //initDocuments();
    };
  }


  private void initRolls() {
    ;
    if(!roleRepository.findByName(RoleName.ROLE_ADMIN).isPresent()){
      Role admin = new Role();
      admin.setName(RoleName.ROLE_ADMIN);
      roleRepository.save(admin);
    }
    if(!roleRepository.findByName(RoleName.ROLE_USER).isPresent()){
      Role user = new Role();
      user.setName(RoleName.ROLE_USER);
      roleRepository.save(user);
    }

  }
 /*
  public void initDocuments() throws IOException {

   String[] jsonDocuments = IOUtils.readZipFileToString("/home/casey/up/uplaw-backend/dump/data/courtlistener/opinions/akd.json.zip").split("\n");
    System.out.println(jsonDocuments[0]);
    List<ObjectNode> documents = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      String content = jsonDocuments[i];
      content = content.substring(0,100);
      //ObjectNode json = new ObjectMapper().readValue(content, ObjectNode.class);
      //documents.add(json);
      Document document1 = Document.builder().title("title").slug("roe_v_wade_298_us_28_1996").content(content).build();
      documentService.createDocument(document1);
    }*/
    /*Document document1 = Document.builder()
            .title("Roe v. Wade, 298 U.S. 28 (1996)")
            .slug("roe_v_wade_298_us_28_1996")
            .content("It is ordered in the court.\nThat the holding is not guilty.\nTherefore, hitherto, it shall be held, not guilt.")
            .build();
    Document document2 = Document.builder()
            .title("BingHole v. Tire Co., Inc., 198 U.S. 38 (1998)")
            .slug("bing_hole_v_tire_co_inc_198_us_38_1998")
            .content("It is ordered in the court.\nThat the holding is not guilty.\nTherefore, hitherto, it shall be held, not guilt.")
            .build();
    Document document3 = Document.builder()
            .title("Pepsi v. Coke Co., Inc., 182 Fed. Rep. 38, 22 (5th Cir. 2008)")
            .slug("pepsi_v_coke_co_,_inc_,_182_fed_rep_38,_22_5_th_cir_2008")
            .content("Here he here he. The court shall hold that no longer is thous. Be it held.")
            .build();
    documentService.createDocument(document1);
    documentService.createDocument(document2);
    documentService.createDocument(document3);
  }
*/


}
