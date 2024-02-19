import React, { useEffect, useState } from "react";
import Table from "react-bootstrap/Table";
import Button from "react-bootstrap/Button";
import ModalForm from "../modalForm";
import axios from "axios";
import moment from "moment";

const Person = () => {
  const [modalShow, setModalShow] = useState({ open: false, person: null });
  const [personList, setPersonList] = useState([]);

  const formatter = new Intl.NumberFormat("en-US", {
    style: "currency",
    currency: "USD",
  });

  const getPersonList = () => {
    axios
      .get("http://localhost:8081/api/persons/getPersonList")
      .then(function (response) {
        if (response.data?.code !== 200) {
          alert(response.data?.error);
        }
        setPersonList(response.data?.responseList ?? []);
      })
      .catch(function (error) {
        alert("Ocurrio un problema");
      })
      .finally(function () {});
  };

  const deletePersonForm = (personForm) => {
    axios
      .delete(`http://localhost:8081/api/persons/delete/${personForm?.id}`)
      .then(function (response) {
        if (response.data?.code === 200) {
          alert(`Persona eliminada`);
          getPersonList();
          return;
        }
        alert(response.data?.error);
      })
      .catch(function (error) {
        alert("Ocurrio un problema");
      })
      .finally(function () {});
  };

  useEffect(() => {
    getPersonList();

    return () => {};
  }, []);

  return (
    <>
      <h1 className="text-center my-4">Crud de personas</h1>
      <div className="mx-5">
        <div>
          <Button
            className="my-2"
            style={{
              float: "right",
            }}
            variant="primary"
            onClick={() =>
              setModalShow({
                open: true,
                person: {
                  id: null,
                  name: "",
                  lastname: "",
                  email: "",
                  birthday: "",
                  salary: 0.0,
                },
              })
            }
          >
            Crear persona
          </Button>

          {modalShow.open && (
            <ModalForm
              open={modalShow.open}
              person={modalShow.person ?? null}
              onHide={(update) => {
                if (update) getPersonList();
                setModalShow({ open: false, person: null });
              }}
            />
          )}
        </div>
        <div className="text-center mt-5">
          {personList?.length > 0 ? (
            <Table bordered hover>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Nombre</th>
                  <th>Apellido</th>
                  <th>Correo eletrónico</th>
                  <th>Fecha de cumpleaños</th>
                  <th>Salario</th>
                </tr>
              </thead>
              <tbody>
                {personList.map((person) => (
                  <tr key={person?.id}>
                    <td>
                      {person?.id}{" "}
                      <span
                        className="ml-2"
                        onClick={() => {
                          setModalShow({ open: true, person: person });
                        }}
                      >
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          viewBox="0 0 20 20"
                          fill="currentColor"
                          className="w-5 h-5"
                          style={{ color: "blue" }}
                          height={24}
                          width={24}
                        >
                          <path d="m5.433 13.917 1.262-3.155A4 4 0 0 1 7.58 9.42l6.92-6.918a2.121 2.121 0 0 1 3 3l-6.92 6.918c-.383.383-.84.685-1.343.886l-3.154 1.262a.5.5 0 0 1-.65-.65Z" />
                          <path d="M3.5 5.75c0-.69.56-1.25 1.25-1.25H10A.75.75 0 0 0 10 3H4.75A2.75 2.75 0 0 0 2 5.75v9.5A2.75 2.75 0 0 0 4.75 18h9.5A2.75 2.75 0 0 0 17 15.25V10a.75.75 0 0 0-1.5 0v5.25c0 .69-.56 1.25-1.25 1.25h-9.5c-.69 0-1.25-.56-1.25-1.25v-9.5Z" />
                        </svg>
                      </span>
                      <span
                        className="ml-2"
                        onClick={() => {
                          if (
                            window.confirm(
                              "¿Está seguro de eliminar el registro?"
                            )
                          ) {
                            deletePersonForm(person);
                          }
                        }}
                      >
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          viewBox="0 0 20 20"
                          fill="currentColor"
                          className="w-5 h-5"
                          style={{ color: "red" }}
                          height={24}
                          width={24}
                        >
                          <path
                            fill-rule="evenodd"
                            d="M8.75 1A2.75 2.75 0 0 0 6 3.75v.443c-.795.077-1.584.176-2.365.298a.75.75 0 1 0 .23 1.482l.149-.022.841 10.518A2.75 2.75 0 0 0 7.596 19h4.807a2.75 2.75 0 0 0 2.742-2.53l.841-10.52.149.023a.75.75 0 0 0 .23-1.482A41.03 41.03 0 0 0 14 4.193V3.75A2.75 2.75 0 0 0 11.25 1h-2.5ZM10 4c.84 0 1.673.025 2.5.075V3.75c0-.69-.56-1.25-1.25-1.25h-2.5c-.69 0-1.25.56-1.25 1.25v.325C8.327 4.025 9.16 4 10 4ZM8.58 7.72a.75.75 0 0 0-1.5.06l.3 7.5a.75.75 0 1 0 1.5-.06l-.3-7.5Zm4.34.06a.75.75 0 1 0-1.5-.06l-.3 7.5a.75.75 0 1 0 1.5.06l.3-7.5Z"
                            clip-rule="evenodd"
                          />
                        </svg>
                      </span>
                    </td>
                    <td>{person?.name}</td>
                    <td>{person?.lastname}</td>
                    <td>{person?.email}</td>
                    <td>{moment(person?.birthday).format("yyyy-MM-DD")}</td>
                    <td>{formatter.format(person?.salary)}</td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <span>No tiene personas registradas</span>
          )}
        </div>
      </div>
    </>
  );
};

export default Person;
