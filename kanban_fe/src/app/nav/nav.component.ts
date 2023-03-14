import { Component, OnInit } from "@angular/core";
import {
  faLightbulb as faSolidLightbulb,
  faDollarSign,
  IconDefinition,
  faBook
} from "@fortawesome/free-solid-svg-icons";
import { faLightbulb as faRegularLightbulb } from "@fortawesome/free-regular-svg-icons";
import { ThemeService } from "src/app/theme/theme.service";
import { ServicesService } from "../services/services.service";

@Component({
  selector: "app-nav",
  templateUrl: "./nav.component.html",
  styleUrls: ["./nav.component.css"]
})
export class NavComponent implements OnInit {
  faLightbulb: IconDefinition =faRegularLightbulb;
  faDollarSign = faBook;
  profileC:any;

  constructor(
    private themeService: ThemeService
    ,private service: ServicesService
    ) {}

  ngOnInit() {
    this.setLightbulb();
    this.profileC=this.service.profileC;
  }

  setLightbulb() {
    if (this.themeService.isDarkTheme()) {
      this.faLightbulb = faRegularLightbulb;
    } else {
      this.faLightbulb = faSolidLightbulb;
    }
  }

  toggleTheme() {
    if (this.themeService.isDarkTheme()) {
      this.themeService.setLightTheme();
    } else {
      this.themeService.setDarkTheme();
    }

    this.setLightbulb();
  }
}
