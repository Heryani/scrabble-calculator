import { useEffect, useRef } from "react";
import { WORD_LENGTH } from "../constants";
import { isValidLetter } from "../utils/validation";
import LetterTile from "./LetterTile";
import "./Rack.css";

type RackProps = {
  word: string[];
  points: number[];
  onWordUpdate: (index: number, letter: string) => void;
  onPointsUpdate: (index: number, letter: string) => void;
  onSubmit: () => void;
};

function Rack({
  word,
  points,
  onWordUpdate,
  onPointsUpdate,
  onSubmit,
}: RackProps) {
  const tileRefs = useRef<Array<HTMLInputElement | null>>(
    Array(WORD_LENGTH).fill(null)
  );

  useEffect(() => {
    const firstTile = tileRefs.current[0];
    firstTile?.focus();
  }, []);

  const handleInput = (index: number, letter: string) => {
    if (isValidLetter(letter) || isClearingInputOnLastTile(index, letter)) {
      onWordUpdate(index, letter);
      onPointsUpdate(index, letter);

      if (letter && index + 1 < WORD_LENGTH) {
        focusAt(index + 1);
      }
    }
  };

  const handleBackspace = (index: number) => {
    const prevIndex = index > 0 ? index - 1 : 0;

    if (currentTileSelectedAndNotEmpty(index)) {
      onWordUpdate(index, "");
      onPointsUpdate(index, "");
    } else {
      onWordUpdate(prevIndex, "");
      onPointsUpdate(prevIndex, "");
    }

    focusAt(prevIndex);
  };

  const handleSubmit = () => {
    onSubmit();
  };

  const isClearingInputOnLastTile = (index: number, letter: string) =>
    index === WORD_LENGTH - 1 && letter === "";

  const currentTileSelectedAndNotEmpty = (index: number) =>
    tileRefs.current[index]?.value !== "";

  const focusAt = (index: number) => {
    const tile = tileRefs.current[index];
    tile?.focus();
  };

  return (
    <>
      <div className="rack">
        {Array.from({ length: WORD_LENGTH }, (_, i) => (
          <LetterTile
            key={i}
            index={i}
            letter={word[i]}
            point={points[i]}
            onChange={handleInput}
            onBackspace={handleBackspace}
            onEnter={handleSubmit}
            ref={(el) => (tileRefs.current[i] = el)}
          ></LetterTile>
        ))}
      </div>
    </>
  );
}

export default Rack;
