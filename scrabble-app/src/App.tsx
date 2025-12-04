import "./App.css";
import ScrabbleGame from "./components/ScrabbleGame";

function App() {
  return (
    <>
      <h1>Scrabble</h1>
      <p>
        Enter a word of up to 10 letters and try to beat the top score. Let's
        Play!
      </p>
      <ScrabbleGame />
    </>
  );
}

export default App;
