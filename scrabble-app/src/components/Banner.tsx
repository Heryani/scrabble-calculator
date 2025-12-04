import { MdError } from "react-icons/md";
import { IoIosCheckmarkCircle } from "react-icons/io";
import "./Banner.css";

type BannerProps = {
  isError: boolean;
  message: string;
};

function Banner({ isError, message }: BannerProps) {
  return (
    <>
      {isError && (
        <div className="error-banner">
          <MdError className="icon" />
          Error: {message}
        </div>
      )}
      {!isError && (
        <div className="success-banner">
          <IoIosCheckmarkCircle className="icon" />
          Success: {message}
        </div>
      )}
    </>
  );
}

export default Banner;
