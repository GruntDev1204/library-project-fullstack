import { useMemo, useRef, useState } from "react"
import { fetchBook } from "../../assets/type/interface"
import baseApi from "../../assets/api/baseApi"
import { formatMoney } from "../../assets/service/format"
import Notification from "../notification/Notification"
import ModalView from "./ModalView"

export default function Similarity({
  category,
  thisB,
}: {
  category: any
  thisB: any
}) {
  const [turnOnFan, setturnOnFan] = useState<boolean>(false)
  const alert = useRef<any>(null)
  const size = 3

  const [index, setIndex] = useState<number>(1)
  const [all, setTotalIndex] = useState<number>(0)

  const [books, getBooks] = useState<fetchBook[]>([])

  const [selectedId, setSelectedId] = useState<number>(0)

  const selectedAction = (id: number) => {
    setSelectedId(id)
  }

  const handleCloseModal = () => {
    setSelectedId(0)
  }

  const fetchBooks = () => {
    baseApi
      .get(
        `books?size=${size}&sortOption=desc&index=${index}&sortByCol=transactionVolume&categoryId=${category}`,
        alert.current
      )
      .then((res: any) => {
        getBooks(res.data.data)
        setTotalIndex(res.data.pageDetail.total_pages)
      })
  }

  const handleNext = () => {
    if (index < all) {
      setIndex(index + 1)
    } else {
      alert.current.showAlert("warning", "No more pages available.")
    }
  }

  const handleBack = () => {
    if (index > 1) {
      setIndex(index - 1)
    } else {
      alert.current.showAlert("warning", "You are already on the first page.")
    }
  }

  useMemo(() => {
    fetchBooks()
  }, [index])

  const getEffect = () => {
    setturnOnFan(true)

    setTimeout(() => {
      setturnOnFan(false)
    }, 5000)
  }

  return (
    <>
      <div
        className={`alert alert-card mt-5 ${turnOnFan ? "effect-fly-up" : ""}`}
      >
        <div className="row mt-4 mb-2">
          <div className="col medium">
            <h3 className=" mb-3 title">
              <i className="fa-solid fa-tag" onClick={getEffect}></i> Similar
            </h3>
          </div>
        </div>
        <div className="row mt-2 mb-4">
          <div className="col-md-6" onClick={handleBack}>
            <button className="button mb-2">
              <i className="fa-solid fa-backward"></i>
            </button>
          </div>
          <div className="col-md-6 flex-end">
            <button className="button mb-2" onClick={handleNext}>
              <i className="fa-solid fa-forward"></i>
            </button>
          </div>
        </div>
        <div className="row mt-4 mb-2">
          {(books && books.length) ? (
            books.map((book: any) =>
              book.id !== thisB ? (
                <div key={book.id} className="col-md-4">
                  <div className="alert flex-col-center card-content">
                    <div className="header-card">
                      {book.promotionValue > 0 && (
                        <h3 className="mb-3 flex-end tag small">
                          -{book.promotionValue}%
                        </h3>
                      )}
                      <img src={book.avatar} alt={book.name} />
                    </div>
                    <div className="body-card">
                      <div className="row">
                        <div className="col">
                          <h3 className="mb-3 book-name"> {book.name}</h3>
                          {book.promotionValue > 0 && (
                            <>
                              <h3 className={`remove small tag-3 `}>
                                {formatMoney(book.price)}{" "}
                              </h3>
                              <h3 className="tag-2 mt-3  ">
                                {` `}
                                {formatMoney(
                                  (book.price * (100 - book.promotionValue)) /
                                    100
                                )}{" "}
                              </h3>
                            </>
                          )}
                          {book.promotionValue <= 0 && (
                            <h3 className={`mb-3 small tag-3`}>
                              {formatMoney(book.price)}{" "}
                            </h3>
                          )}
                        </div>
                      </div>
                    </div>
                    <div className="footer-card">
                      {book.quantity > 0 && (
                        <div className="row">
                          <div className="col-md-4 flex-start">
                            <button className="button small">
                              <i className="fa-solid fa-cart-plus"></i>
                            </button>
                          </div>
                          <div className="col-md-4 flex-center">
                            <button className="button small">
                              <i className="fa-solid fa-heart"></i>
                            </button>
                          </div>
                          <div className="col-md-4 flex-end">
                            <button
                              className="button small ml-2"
                              onClick={() => selectedAction(book.id)}
                            >
                              <i className="fa-regular fa-eye"></i>
                            </button>
                          </div>
                        </div>
                      )}
                    </div>
                    {book.quantity <= 0 && (
                      <div className="alert notice small">
                        <span> Out of stock! </span>
                      </div>
                    )}
                  </div>
                </div>
              ) : null
            )
          ) : (
            <h3 className="text-center medium">
              {" "}
              <i className="fa-solid fa-spinner effect-fan" />
              Loading...
            </h3>
          )}
        </div>
      </div>
      <Notification ref={alert} />
      {selectedId > 0 ? (
        <ModalView id={selectedId} onClose={handleCloseModal}></ModalView>
      ) : (
        ""
      )}
    </>
  )
}
