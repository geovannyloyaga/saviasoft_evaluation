import React, { useEffect, useState } from "react";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import axios from "axios";
import moment from "moment";

const ModalForm = ({ open, person, onHide }) => {
  const [personForm, setPersonForm] = useState(person);
  const [countryList, setCountryList] = useState([]);

  const changeValueForm = (name, value) => {
    setPersonForm({ ...personForm, [name]: value });
  };

  const getCountryList = () => {
    axios
      .get("http://localhost:8081/api/countries/getCountryList")
      .then(function (response) {
        if (response.data?.code !== 200) {
          alert(response.data?.error);
        }
        setCountryList(response.data?.responseList ?? []);
      })
      .catch(function (error) {
        alert("Ocurrio un problema");
      })
      .finally(function () {});
  };

  const processForm = () => {
    if (personForm?.id) {
      updatePersonForm();
    } else {
      savePersonForm();
    }
  };

  const updatePersonForm = () => {
    axios
      .put(
        `http://localhost:8081/api/persons/update/${personForm?.id}`,
        personForm
      )
      .then(function (response) {
        if (response.data?.code === 200) {
          alert(`Persona actualizada`);
          onHide(true);
          return;
        }
        alert(response.data?.error);
      })
      .catch(function (error) {
        alert("Ocurrio un problema");
      })
      .finally(function () {});
  };

  const savePersonForm = () => {
    axios
      .post(`http://localhost:8081/api/persons/save`, personForm)
      .then(function (response) {
        if (response.data?.code === 200) {
          alert(`Persona creada`);
          onHide(true);
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
    getCountryList();

    return () => {};
  }, []);

  return (
    <Modal
      show={open}
      size="lg"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Formulario de persona
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={processForm.bind(this)}>
          <Row>
            <Col xs={6}>
              <Form.Group
                className="mb-3"
                controlId="exampleForm.ControlInput1"
              >
                <Form.Label>Nombre</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Juan"
                  maxLength={60}
                  value={personForm?.name}
                  onChange={(event) =>
                    changeValueForm("name", event.target.value)
                  }
                />
              </Form.Group>
            </Col>
            <Col xs={6}>
              <Form.Group
                className="mb-3"
                controlId="exampleForm.ControlInput1"
              >
                <Form.Label>Apellido</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="Pérez"
                  maxLength={100}
                  value={personForm?.lastname}
                  onChange={(event) =>
                    changeValueForm("lastname", event.target.value)
                  }
                />
              </Form.Group>
            </Col>
            <Col xs={4}>
              <Form.Group
                className="mb-3"
                controlId="exampleForm.ControlInput1"
              >
                <Form.Label>País</Form.Label>
                <Form.Select
                  onChange={(event) => {
                    changeValueForm("country_id", event.target.value);
                  }}
                >
                  {countryList.map((country) => (
                    <option value={country?.id}>{country?.nombre}</option>
                  ))}
                </Form.Select>
              </Form.Group>
            </Col>
            <Col xs={8}>
              <Form.Group
                className="mb-3"
                controlId="exampleForm.ControlInput1"
              >
                <Form.Label>Correo electrónico</Form.Label>
                <Form.Control
                  type="email"
                  placeholder="name@example.com"
                  value={personForm?.email}
                  onChange={(event) =>
                    changeValueForm("email", event.target.value)
                  }
                />
              </Form.Group>
            </Col>
            <Col xs={6}>
              <Form.Group
                className="mb-3"
                controlId="exampleForm.ControlInput1"
              >
                <Form.Label>Fecha cumpleaños</Form.Label>
                <Form.Control
                  type="date"
                  placeholder="dd/mm/aaaa"
                  value={
                    personForm?.birthday
                      ? moment(personForm?.birthday).format("yyyy-MM-DD")
                      : ""
                  }
                  onChange={(event) =>
                    changeValueForm(
                      "birthday",
                      new Date(event.target.value + " 00:00:00")
                    )
                  }
                />
              </Form.Group>
            </Col>

            <Col xs={6}>
              <Form.Group
                className="mb-3"
                controlId="exampleForm.ControlInput1"
              >
                <Form.Label>Salario $</Form.Label>
                <Form.Control
                  type="number"
                  placeholder="123,56"
                  value={personForm?.salary}
                  onChange={(event) =>
                    changeValueForm("salary", event.target.value)
                  }
                />
              </Form.Group>
            </Col>
            <Col xs={12} className="text-center">
              <Button type="submit" className="btn btn-success">
                Guardar
              </Button>
            </Col>
          </Row>
        </Form>
      </Modal.Body>
    </Modal>
  );
};

export default ModalForm;
