import { useState } from "react"
import { videos } from "../../../assets/service/variable"

export default function Slide({
  turnOnSlide = true,
}: {
  turnOnSlide?: boolean
}) {
  const [currentIndex, setCurrentIndex] = useState<number>(0)
  const nextVideo = () => {
    setCurrentIndex((prevIndex) => (prevIndex + 1) % videos.length)
  }

  const backVideo = () => {
    setCurrentIndex((prevIndex) => (prevIndex - 1) % videos.length)
  }

  return (
    <div
      className="alert alert-form p-4  mt-5"
      style={{ display: turnOnSlide ? "flex" : "none" }}
    >
      <div className="row">
        <div className="col-md-6">
          <button className="button mb-2" onClick={backVideo}>
            <i className="fa-solid fa-backward"></i>
          </button>
        </div>
        <div className="col-md-6 flex-end">
          <button className="button mb-2" onClick={nextVideo}>
            <i className="fa-solid fa-forward"></i>
          </button>
        </div>
      </div>
      <div className="slide-container ">
        <div
          className="slider"
          style={{ transform: `translateX(-${currentIndex * 100}%)` }}
        >
          {videos.map((video, index) => (
            <div className="slide" key={index}>
              <video controls autoPlay muted>
                <source src={video} type="video/mp4" />
                Trình duyệt của bạn không hỗ trợ thẻ video.
              </video>
            </div>
          ))}
        </div>
      </div>
    </div>
  )
}
