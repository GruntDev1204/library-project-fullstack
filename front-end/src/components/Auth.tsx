import { Outlet } from "react-router-dom";

export default function Auth() {
    return (
        <div className="body">
            <div className="container">
                <Outlet />
            </div>
        </div>
    )
}