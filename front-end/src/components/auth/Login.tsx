import { Link } from "react-router-dom"

export default function Login() {
  return (
    <div className="alert alert-form form-login">
      <div className="row mt-4 mb-2">
        <div className="col medium">
          <h3 className="text-center mb-3 title">
            <i className="fa-solid fa-user mr-2" />
            Login
          </h3>
        </div>
      </div>

      <div className="row mt-5 mb-5">
        <div className="col">
          <div className="form-group">
            <label className="mb-2">Email address</label>
            <input type="email" className="form-control mb-4" />
            <small className="form-text text-muted ">
              We'll never share your email with anyone else.
            </small>
          </div>
          <div className="form-group mt-5  ">
            <label className="mb-2">Password </label>
            <input type="password" className="form-control mb-2" />
          </div>

          <button type="submit" className="button mt-5 small">
            Login
          </button>
          <button type="submit" className="button mt-5 small ml-3">
            <Link to="/auth/sign-up">Register?</Link>
          </button>
        </div>
      </div>
    </div>
  )
}
