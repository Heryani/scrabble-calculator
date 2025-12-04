import { WORD_LENGTH } from "../constants";
import "./LetterTile.css";

type LetterTileProps = {
  index: number;
  letter: string;
  point: number;
  onChange: (index: number, letter: string) => void;
  onBackspace: (index: number) => void;
  onEnter: () => void;
  ref?: (el: HTMLInputElement | null) => void;
};

function LetterTile({
  index,
  letter,
  point,
  onChange,
  onBackspace,
  onEnter,
  ref,
}: LetterTileProps) {
  const isLastTileFilled = letter !== "" && index === WORD_LENGTH - 1;

  const handleKeyPress = (e) => {
    if (e.key === "Backspace") {
      if (isLastTileFilled) {
        onChange(index, "");
      } else {
        // e.preventDefault();
        onBackspace(index);
      }
    } else if (e.key === "Enter") {
      onEnter();
      e.currentTarget.blur();
    }
  };

  return (
    <>
      <div className="tile">
        <input
          ref={ref}
          className="letter-input"
          type="text"
          maxLength={1}
          value={letter}
          onChange={(e) => onChange(index, e.target.value.toUpperCase())}
          onKeyDown={handleKeyPress}
        />
        <span className="point-badge">{point}</span>
      </div>
    </>
  );
}

export default LetterTile;
