package pl.sebastianstrzalkowski.gasbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sebastianstrzalkowski.gasbackend.entities.GasPriceDao;
import pl.sebastianstrzalkowski.gasbackend.models.GasPrice;
import pl.sebastianstrzalkowski.gasbackend.models.Result;
import pl.sebastianstrzalkowski.gasbackend.service.GasService;
import pl.sebastianstrzalkowski.gasbackend.service.GasServiceRest;


@Controller()
@RequestMapping(value = "/eth")
public class EtherGasPriceController {

    @Autowired
    private GasService gasService;

    @GetMapping(value = "/price")
    public ResponseEntity<Result> helloWorld(){
        return ResponseEntity.ok(gasService.getGasPrice());
    }

}
