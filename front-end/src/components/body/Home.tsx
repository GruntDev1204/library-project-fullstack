import { useRef, useState } from "react"
import Trending from "./active/Trending"
import Popular from "./active/Popular"
import New from "./active/New"
import Slide from "./active/Slide"
import Sale from "./active/Sale"

const Home = () => {
  const [turnOnSlide, setTurnOnSlide] = useState<boolean>(true)
  const [openMenu, setOpenMenu] = useState<boolean>(true)

  const trendingRef = useRef<HTMLDivElement>(null)
  const popularRef = useRef<HTMLDivElement>(null)
  const lastestRef = useRef<HTMLDivElement>(null)
  const saleRef = useRef<HTMLDivElement>(null)

  const scrollToSection = (ref: React.RefObject<HTMLDivElement>) => {
    if (ref.current) {
      const elementPosition =
        ref.current.getBoundingClientRect().top + window.pageYOffset
      window.scrollTo({
        top: elementPosition - 100,
        behavior: "smooth",
      })
    }
  }

  const menuOptions = [
    {
      name: "Lastest",
      href: "/#new",
      action: () => scrollToSection(lastestRef),
      icon: <i className="fa-solid fa-square-up-right"></i>,
    },
    {
      name: "Trending",
      href: "/#trending",
      action: () => scrollToSection(trendingRef),
      icon: <i className="fa-solid fa-fire"></i>,
    },
    {
      name: "Popular",
      href: "/#popular",
      action: () => scrollToSection(popularRef),
      icon: <i className="fa-solid fa-star"></i>,
    },
    {
      name: "Sales ",
      href: "/#sale",
      action: () => scrollToSection(saleRef),
      icon: <i className="fa-solid fa-bolt"></i>,
    },
    {
      name: "Your Cart",
      href: "/favorites",
      icon: <i className="fa-solid fa-cart-shopping"></i>,
    },
  ]

  const handleSlide = () => {
    setTurnOnSlide(!turnOnSlide)
  }

  const openMenuAction = () => {
    setOpenMenu(!openMenu)
  }

  return (
    <>
      <button className="button option-button " onClick={openMenuAction}>
        {" "}
        <i className="fa-solid fa-list medium"></i>
      </button>

      <div
        className="alert alert-form mb-5 p-3"
        style={{ display: openMenu ? "flex" : "none" }}
      >
        <div className="row text-center">
          <div className="col-md-2">
            <button className="button small" onClick={handleSlide}>
              Slide :{" "}
              {turnOnSlide ? (
                <>
                  <i className="fa-solid fa-heart"></i>
                </>
              ) : (
                <>
                  <i className="fa-solid fa-heart-crack"></i>
                </>
              )}
            </button>
          </div>
          {menuOptions.map((option, index) => (
            <div className="col-md-2 text-center" key={index}>
              <button className="button small" onClick={option.action}>
                {option.icon} {option.name}
              </button>
            </div>
          ))}
        </div>
      </div>

      <Slide turnOnSlide={turnOnSlide} />

      <div ref={lastestRef} id="new">
        <New />
      </div>
      <div ref={trendingRef} id="trending">
        <Trending />
      </div>
      <div ref={popularRef} id="popular">
        <Popular />
      </div>
      <div ref={saleRef} id="sale">
        <Sale />
      </div>
    </>
  )
}

export default Home
