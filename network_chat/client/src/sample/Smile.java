package sample;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.util.ArrayList;

public class Smile {
    private Text text;
    private String[][] smiles=new String[6][];
    private ArrayList<Node> PiecesOfText = new ArrayList<>();
    Smile(Text text)
    {
        this.text=text;
        smiles[0]=new String[]{":)","src/smile.png"};
        smiles[1]=new String[]{":(","src/sadsmile.png"};
        smiles[2]=new String[]{";(","src/cry.png"};
        smiles[3]=new String[]{":D","src/lol.png"};
        smiles[4]=new String[]{";)","src/wink.png"};
        smiles[5]=new String[]{"}:)","src/evil.png"};
        while (firstSmiley()>-1)
            textTosmile();
        PiecesOfText.add(this.text);
    }
    private int firstSmiley()
    {
        int smileyNomber=-1;
        int firstChar=-1;
        String str=text.getText();
        for(int i=0;i<smiles.length;i++)
        {
            if((str.indexOf(smiles[i][0])<firstChar &&str.indexOf(smiles[i][0])!=-1) || firstChar==-1)
            {
                smileyNomber=i;
                firstChar=str.indexOf(smiles[i][0]);
            }
        }
        return (firstChar==-1?-1:smileyNomber);
    }
    private void textTosmile()
    {
        int smileNumber=firstSmiley();
        String smile=smiles[smileNumber][0];
        String PathToFile=smiles[smileNumber][1];
        Image image = new Image("file:"+PathToFile);
        ImageView imageView = new ImageView(image);
        int ConterSmile = text.getText().indexOf(smile);
        Text beforeSmile=new Text(text.getText().substring(0,text.getText().indexOf(smile)));
        PiecesOfText.add(beforeSmile);
        PiecesOfText.add(imageView);
        text=new Text(text.getText().substring(ConterSmile+smile.length()));
    }

    public ArrayList<Node> getPiecesOfText() {
        return PiecesOfText;
    }
}