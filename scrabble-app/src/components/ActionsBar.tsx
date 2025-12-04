import "./ActionsBar.css";
import Button from "./Button";

type ActionsBarProps = {
  onResetTiles: () => void;
  onSaveScore: () => void;
  onViewTopScores: () => void;
  disableSaveScore: boolean;
  topScoresButtonName: string;
};

function ActionsBar({
  onResetTiles,
  onSaveScore,
  onViewTopScores,
  disableSaveScore,
  topScoresButtonName,
}: ActionsBarProps) {
  return (
    <>
      <div className="bar">
        <Button name="Reset Tiles" onClick={onResetTiles} />
        <Button
          name="Save Score"
          onClick={onSaveScore}
          disable={disableSaveScore}
        />
        <Button name={topScoresButtonName} onClick={onViewTopScores} />
      </div>
    </>
  );
}

export default ActionsBar;
