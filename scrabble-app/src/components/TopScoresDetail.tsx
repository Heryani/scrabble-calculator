import { IoMdTrophy } from "react-icons/io";
import "./TopScoresDetail.css";

type TopScoresDetailProps = {
  scoreList: { word: string; value: number }[];
};

function TopScoresDetail({ scoreList }: TopScoresDetailProps) {
  return (
    <>
      <div className="top-scores-detail">
        <div>
          <IoMdTrophy size={32} className="icon" />
          <h3>Top 10 Scores:</h3>
        </div>
        <ol className="score-list">
          {scoreList.map((score, index) => (
            <li key={index}>
              {score.word} - {score.value}
            </li>
          ))}
        </ol>
      </div>
    </>
  );
}

export default TopScoresDetail;
