/*Ejercicio SQL
Escribir una consulta SQL que traiga todos los clientes que han comprado en total más de 100,000$ en los últimos 12 meses usando las siguientes tablas: 

Clientes: ID, Nombre, Apellido

Ventas: Fecha, Sucursal, Numero_factura, Importe, Id_cliente*/

SELECT c.ID, c.Nombre, c.Apellido
FROM Clientes c
JOIN Ventas v ON c.ID = v.Id_cliente
WHERE v.Fecha >= DATEADD(MONTH, -12, GETDATE())  -- Obtener ventas de los últimos 12 meses
GROUP BY c.ID, c.Nombre, c.Apellido
HAVING SUM(v.Importe) > 100000;  -- Total de compras superior a 100,000$