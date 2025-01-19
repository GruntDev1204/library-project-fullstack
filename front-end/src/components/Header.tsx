import { Link } from "react-router-dom"

const Header = () => {
  return (
    <div className="header">
      <div className="container">
        <div className="header-left">
          <i className="fa-solid fa-building-columns medium mr-2 "></i>{" "}
          <p className="medium">
            {" "}
            <Link to="" className="link">
              Library Project{" "}
            </Link>{" "}
          </p>
        </div>
        <div className="header-right">
          <button className="button">
            <Link to="/auth/sign-up">
              {" "}
              <i className="fa-solid fa-user-plus"></i> Register{" "}
            </Link>
          </button>
          <button className="button">
            <Link to="/auth/login">
              {" "}
              <i className="fa-solid fa-user"></i> Login {" "}
            </Link>
          </button>
        </div>
      </div>
    </div>
  )
}

export default Header
