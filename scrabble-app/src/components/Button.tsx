import "./Button.css";

type ButtonProps = {
  name: string;
  disable?: boolean;
  onClick: () => void;
};

function Button({ name, disable, onClick }: ButtonProps) {
  return (
    <>
      <button disabled={disable} className="btn" onClick={onClick}>
        {name}
      </button>
    </>
  );
}

export default Button;
