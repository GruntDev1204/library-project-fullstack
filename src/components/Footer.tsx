import { avatar, avatarAdmin, discord, fb, github, gitlab } from "../assets/service/variable"

export default function Footer() {
  return (
    <div className="footer">
      <div className="container">
        <div className="footer-left">
          <img src={avatarAdmin} alt="avatar" className="avatar-admin" />
          <div className="row row-col ml-2">
            <small>Template have been created by Mr.Grunt</small>
            <small>
              <i className="fa-solid fa-envelope"></i> hotrungto@gmail.com{" "}
            </small>
          </div>
        </div>
        <div className="footer-right">
          <button className="button">
            {" "}
            <i className="fa-brands fa-gitlab"></i>{" "}
            <a href={gitlab} target="_blank">
              {" "}
              Gitlab
            </a>
          </button>
          <button className="button">
            {" "}
            <i className="fa-brands fa-github"></i>{" "}
            <a href={github} target="_blank">
              {" "}
              Github
            </a>
          </button>
          <button className="button">
            {" "}
            <i className="fa-brands fa-facebook"></i>{" "}
            <a href={fb} target="_blank">
              {" "}
              Facebook
            </a>
          </button>
          <button className="button">
            {" "}
            <i className="fa-brands fa-discord"></i>{" "}
            <a href={discord} target="_blank">
              {" "}
              Discord
            </a>
          </button>
        </div>
      </div>
    </div>
  )
}
