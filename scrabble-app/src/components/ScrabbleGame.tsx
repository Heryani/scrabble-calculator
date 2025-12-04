import { useState, useMemo } from "react";
import { AxiosError } from "axios";
import { WORD_LENGTH } from "../constants";
import { getTopScores, saveScore } from "../services/scoreService";
import { getPoint } from "../services/scoringRuleService";
import Rack from "./Rack";
import ScoreDetail from "./ScoreDetail";
import ActionsBar from "./ActionsBar";
import Banner from "./Banner";
import TopScoresDetail from "./TopScoresDetail";

type Score = {
  id: number;
  word: string;
  value: number;
};

type ErrorResponse = {
  status: number;
  errorMessage: string;
};

function ScrabbleGame() {
  const [word, setWord] = useState<string[]>(Array(WORD_LENGTH).fill(""));
  const [points, setPoints] = useState<number[]>(Array(WORD_LENGTH).fill(0));
  const [topScores, setTopScores] = useState<Score[]>([]);
  const [isTopScoresShown, setIsTopScoresShown] = useState(false);
  const [isSaved, setIsSaved] = useState(false);
  const [hasError, setHasError] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const scoreValue = useMemo(() => points.reduce((a, b) => a + b, 0), [points]);
  const wordString = useMemo(() => word.join(""), [word]);
  const shouldDisableSaveScore =
    hasError || wordString.length === 0 || scoreValue === 0;

  const handleWordUpdate = (index: number, letter: string) => {
    setWord((prevWord) => {
      const newWord = [...prevWord];
      newWord[index] = letter;
      return newWord;
    });
  };

  const handlePointsUpdate = async (index: number, letter: string) => {
    try {
      const point = letter ? await getPoint(letter) : 0;
      setPoints((prevPoints) => {
        const newPoints = [...prevPoints];
        newPoints[index] = point;
        return newPoints;
      });
      clearError();
    } catch (error) {
      setHasError(true);
      setErrorMessage(
        `Could not fetch point for letter ${letter}: ` +
          (error as AxiosError).message
      );
    }
  };

  const handleSaveScore = async () => {
    try {
      const score = { word: wordString, value: scoreValue };
      await saveScore(score);
      setIsSaved(true);
      await updateTopScores();
      setTimeout(() => {
        setIsSaved(false);
        handleResetTiles();
      }, 3000);
    } catch (error) {
      setHasError(true);
      setErrorMessage((error as ErrorResponse).errorMessage);
      setIsSaved(false);
    }
  };

  const handleResetTiles = () => {
    setWord(Array(WORD_LENGTH).fill(""));
    setPoints(Array(WORD_LENGTH).fill(0));
    clearError();
  };

  const updateTopScores = async () => {
    try {
      const data = await getTopScores();
      setTopScores(data);
      if (isSaved && hasError) {
        clearError();
      }
    } catch (error) {
      setHasError(true);
      setErrorMessage((error as AxiosError).message);
      setTopScores([]);
    }
  };

  const clearError = () => {
    setHasError(false);
    setErrorMessage("");
  };

  const handleViewTopScores = async () => {
    toggleTopScoresShown();
    if (!isTopScoresShown) {
      await updateTopScores();
    }
  };

  const toggleTopScoresShown = () => {
    setIsTopScoresShown(!isTopScoresShown);
  };

  return (
    <>
      <div>
        <Rack
          word={word}
          points={points}
          onWordUpdate={handleWordUpdate}
          onPointsUpdate={handlePointsUpdate}
          onSubmit={handleSaveScore}
        />
        <ScoreDetail value={scoreValue} />
        <ActionsBar
          onResetTiles={handleResetTiles}
          onSaveScore={handleSaveScore}
          onViewTopScores={handleViewTopScores}
          disableSaveScore={shouldDisableSaveScore}
          topScoresButtonName={
            isTopScoresShown ? "Hide Top Scores" : "View Top Scores"
          }
        />
        {hasError && <Banner isError={true} message={errorMessage} />}
        {isSaved && (
          <Banner
            isError={false}
            message={`The word ${wordString} with score ${scoreValue} is saved`}
          />
        )}
        {isTopScoresShown && <TopScoresDetail scoreList={topScores} />}
      </div>
    </>
  );
}

export default ScrabbleGame;
