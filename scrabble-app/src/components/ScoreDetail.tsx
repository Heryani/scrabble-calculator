import "./ScoreDetail.css";

type ScoreDetailProps = {
  value: number;
};

function ScoreDetail({ value }: ScoreDetailProps) {
  return (
    <>
      <div className="score-detail">Score: {value}</div>
    </>
  );
}

export default ScoreDetail;
