package login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class termsPage {

    /**
     * Terms of Use
     */

    @FXML
    private JFXButton btn_ignore;

    @FXML
    private JFXButton btn_agree;

    @FXML
    private JFXTextArea textArea_terms;

    @FXML
    public void initialize(){
        textArea_terms.setText("非常歡迎您光臨「工作研究公式」（以下簡稱本App），為了讓您能夠安心使用本App的各項服務與資訊，特此向您說明本App的隱私權保護政策，以保障您的權益，請您詳閱下列內容：\n" +
                "\n" +
                "一、隱私權保護政策的適用範圍\n" +
                "隱私權保護政策內容，包括本App如何處理在您使用網站服務時收集到的個人識別資料。隱私權保護政策不適用於本App以外的相關連結網站，也不適用於非本App所委託或參與管理的人員。\n" +
                "\n" +
                "二、個人資料的蒐集、處理及利用方式\n" +
                "當您造訪本App或使用本App所提供之功能服務時，我們將視該服務功能性質，請您提供必要的個人資料，並在該特定目的範圍內處理及利用您的個人資料；非經您書面同意，本App不會將個人資料用於其他用途。\n" +
                "本App在您使用服務信箱、問卷調查等互動性功能時，會保留您所提供的姓名、電子郵件地址、聯絡方式及使用時間等。\n" +
                "於一般瀏覽時，伺服器會自行記錄相關行徑，包括您使用連線設備的IP位址、使用時間、使用的瀏覽器、瀏覽及點選資料記錄等，做為我們增進網站服務的參考依據，此記錄為內部應用，決不對外公佈。\n" +
                "為提供精確的服務，我們會將收集的問卷調查內容進行統計與分析，分析結果之統計數據或說明文字呈現，除供內部研究外，我們會視需要公佈統計數據及說明文字，但不涉及特定個人之資料。\n" +
                "三、資料之保護\n" +
                "本App主機均設有防火牆、防毒系統等相關的各項資訊安全設備及必要的安全防護措施，加以保護網站及您的個人資料採用嚴格的保護措施，只由經過授權的人員才能接觸您的個人資料，相關處理人員皆簽有保密合約，如有違反保密義務者，將會受到相關的法律處分。\n" +
                "如因業務需要有必要委託其他單位提供服務時，本App亦會嚴格要求其遵守保密義務，並且採取必要檢查程序以確定其將確實遵守。\n" +
                "四、網站對外的相關連結\n" +
                "本App的網頁提供其他網站的網路連結，您也可經由本App所提供的連結，點選進入其他網站。但該連結網站不適用本App的隱私權保護政策，您必須參考該連結網站中的隱私權保護政策。\n" +
                "\n" +
                "五、與第三人共用個人資料之政策\n" +
                "本App絕不會提供、交換、出租或出售任何您的個人資料給其他個人、團體、私人企業或公務機關，但有法律依據或合約義務者，不在此限。\n" +
                "\n" +
                "前項但書之情形包括不限於：\n" +
                "\n" +
                "經由您書面同意。\n" +
                "法律明文規定。\n" +
                "為免除您生命、身體、自由或財產上之危險。\n" +
                "與公務機關或學術研究機構合作，基於公共利益為統計或學術研究而有必要，且資料經過提供者處理或蒐集著依其揭露方式無從識別特定之當事人。\n" +
                "當您在網站的行為，違反服務條款或可能損害或妨礙網站與其他使用者權益或導致任何人遭受損害時，經網站管理單位研析揭露您的個人資料是為了辨識、聯絡或採取法律行動所必要者。\n" +
                "有利於您的權益。\n" +
                "本App委託廠商協助蒐集、處理或利用您的個人資料時，將對委外廠商或個人善盡監督管理之責。\n" +
                "六、Cookie之使用\n" +
                "為了提供您最佳的服務，本App會在您的電腦中放置並取用我們的Cookie，若您不願接受Cookie的寫入，您可在您使用的瀏覽器功能項中設定隱私權等級為高，即可拒絕Cookie的寫入，但可能會導至網站某些功能無法正常執行 。\n" +
                "\n" +
                "七、隱私權保護政策之修正\n" +
                "本App隱私權保護政策將因應需求隨時進行修正，修正後的條款將刊登於網站上。");
    }

    @FXML
    void agreeTerms(ActionEvent event) {
        registerPage.termsCheck = true;
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    void ignoreTerms(ActionEvent event) {
        registerPage.termsCheck = false;
    }
}
