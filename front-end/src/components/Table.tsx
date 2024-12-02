export default function Table() {
    const avatar : string = "https://scontent.fhan2-4.fna.fbcdn.net/v/t39.30808-6/293587659_140454775327890_5738352014150807079_n.jpg?_nc_cat=110&ccb=1-7&_nc_sid=833d8c&_nc_ohc=8y0UquKNeqUQ7kNvgFvnGJD&_nc_zt=23&_nc_ht=scontent.fhan2-4.fna&_nc_gid=A7B42oz9dsq1d0WK8gbQWXY&oh=00_AYCQ5uF10wgtM_oLgTFzJTecknCCIFZn2-2liSQVF7IZxg&oe=6753D133"
    return (
        <div className="container">
            <table className="table">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">First</th>
                        <th scope="col">avatar</th>
                        <th scope="col">Last</th>
                        <th scope="col">Handle</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th scope="row">1</th>
                        <td>Mark</td>
                        <td><img src={avatar} className="avatar" /></td>
                        <td>Otto</td>
                        <td>@mdo</td>
                    </tr>
                    <tr>
                        <th scope="row">2</th>
                        <td>Jacob</td>
                        <td><img src={avatar} className="avatar" /></td>
                        <td>Thornton</td>
                        <td>@fat</td>
                    </tr>
                    <tr>
                        <th scope="row">3</th>
                        <td>Larry</td>
                        <td><img src={avatar} className="avatar" /></td>
                        <td>the Bird</td>
                        <td>@twitter</td>
                    </tr>
                </tbody>
            </table>
        </div>
    )
}