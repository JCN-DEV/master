'use strict';

angular.module('stepApp').controller('RisNewAppFormDialogController',
    ['$scope', '$state', '$stateParams', 'entity', 'RisNewAppForm', 'RisNewAppFormTwo', 'District', 'positionbycircular', 'Upazila', 'getDesignation', 'getCircularNumber', 'risNewAppFormTwoDetail', 'RisAppFormEduQ', 'GetEducations', 'DateUtils',
        function ($scope, $state, $stateParams, entity, RisNewAppForm, RisNewAppFormTwo, District, positionbycircular, Upazila, getDesignation, getCircularNumber, risNewAppFormTwoDetail, RisAppFormEduQ, GetEducations, DateUtils) {

            $scope.risNewAppForm = entity;
            $scope.risNewAppFormTwo = {};
            $scope.risAppFormEduQ = {};
            $scope.cicru = {};
            $scope.circular = {};
            $scope.items = [];
            $scope.d = '';

            $scope.districts = District.query();
            $scope.upazilas = Upazila.query();

            $scope.calendar = {
                opened: {},
                dateFormat: 'yyyy-MM-dd',
                dateOptions: {},
                open: function ($event, which) {
                    $event.preventDefault();
                    $event.stopPropagation();
                    $scope.calendar.opened[which] = true;
                }
            };


            $scope.load = function (id) {
                RisNewAppForm.get({id: id}, function (result) {
                    $scope.risNewAppForm = result;
                    console.log("This the Load function");
                    console.log($scope.risNewAppForm);
                    $scope.educationsItem = GetEducations.query({id: id}, function (result) {

                        angular.forEach($scope.educationsItem, function (item) {
                            $scope.items.push(item);
                            console.log("question add : ", item);
                        });

                        console.log('Pushpa I hate tears: ' + $scope.educationsItem);
                    });
                });
            };

            $scope.designations = getDesignation.query();
            console.log($scope.designations);

            $scope.circulars = getCircularNumber.query();
            console.log($scope.circulars);


            $scope.changeSameAddress = function (data) {
                console.log('checked -----clicked' + data);
                if (data) {

                    $scope.risNewAppForm.holdingNameBnPermanent = $scope.risNewAppForm.holdingNameBnPresent;
                    $scope.risNewAppForm.villageBnPermanent = $scope.risNewAppForm.villageBnPresent;
                    $scope.risNewAppForm.unionBnPermanent = $scope.risNewAppForm.unionBnPresent;
                    $scope.risNewAppForm.poBnPermanent = $scope.risNewAppForm.poBnPresent;
                    $scope.risNewAppForm.poCodeBnPermanent = $scope.risNewAppForm.poCodeBnPresent;
                } else {
                    $scope.risNewAppForm.holdingNameBnPermanent = null;
                    $scope.risNewAppForm.villageBnPermanent = null;
                    $scope.risNewAppForm.unionBnPermanent = null;
                    $scope.risNewAppForm.poBnPermanent = null;
                    $scope.risNewAppForm.poCodeBnPermanent = null;
                }
            };
            $scope.changeEnSameAddress = function (data) {
                console.log('checked -----clicked' + data);
                if (data) {

                    $scope.risNewAppForm.holdingNameEnPermanent = $scope.risNewAppForm.holdingNameEnPresent;
                    $scope.risNewAppForm.villageEnPermanent = $scope.risNewAppForm.villageEnPresent;
                    $scope.risNewAppForm.unionEnPermanent = $scope.risNewAppForm.unionEnPresent;
                    $scope.risNewAppForm.poEnPermanent = $scope.risNewAppForm.poEnPresent;
                    $scope.risNewAppForm.poCodeEnPermanent = $scope.risNewAppForm.poCodeEnPresent;
                } else {
                    $scope.risNewAppForm.holdingNameEnPermanent = null;
                    $scope.risNewAppForm.villageEnPermanent = null;
                    $scope.risNewAppForm.unionEnPermanent = null;
                    $scope.risNewAppForm.poEnPermanent = null;
                    $scope.risNewAppForm.poCodeEnPermanent = null;
                }
            };


            var onSaveFinished = function (result) {
                $scope.isSaving = false;

                $scope.risNewAppForm = result;
                $scope.risNewAppFormTwo.risNewAppForm = result;
                console.log("=============ris two==============");
                console.log($scope.risNewAppForm.id);

                $scope.id = $scope.risNewAppForm.id;

                console.log("=============ris two==============");
                console.log($scope.risNewAppFormTwo);
                RisNewAppFormTwo.save($scope.risNewAppFormTwo);

                angular.forEach($scope.items, function (item) {
                    console.log("=============First Step==============");

                    if ($stateParams.id != null) {
                        console.log("=============Second Step==============");

                        console.log('>>>>>>> update');
                        console.log(item);
                        item.risNewAppForm = result;
                        RisAppFormEduQ.update(item);
                        console.log("=============Third Step==============");
                    } else {
                        console.log("=============Fourth Step==============");

                        console.log('>>>>>>> save');
                        console.log(item);
                        item.risNewAppForm = result;
                        RisAppFormEduQ.save(item);
                        console.log("=============Fifth Step==============");
                    }
                });
                $scope.$emit('stepApp:risNewAppFormUpdate', result);
                $state.go('risNewAppForm', null, {reload: true});
            };

            var onSaveUpdated = function (result) {
                $scope.isSaving = false;
                console.log("=========This is Second updated ========");
                RisNewAppFormTwo.update($scope.risNewAppFormTwo, updateItems);
                console.log("=========This is Second updated 2========");
            };


            var updateItems = function (result) {
                $scope.isSaving = false;

                console.log("=============Third Update Step==============");
                angular.forEach($scope.items, function (item) {
                    console.log("=============First update Step==============");
                    if ($stateParams.id != null) {
                        console.log("=============Second Update Step==============");
                        console.log(item);
                        /*item.risNewAppForm = result;*/
                        RisAppFormEduQ.update(item);
                        console.log("=============Third Step update==============");
                    }
                });

                $state.go('risNewAppForm', null, {reload: true});
            };

            $scope.amarDate = function (date) {
                $scope.date = date;
                if ($scope.date == null) {
                    $scope.birthError = "Please Input correct Birthday";
                    console.log($scope.birthError);
                } else {

                    var d = $scope.date;
                    var birthyear = d.getFullYear();
                    var birthmonth = d.getMonth();
                    var birthdate = d.getDay();
                    console.log("picked date " + n);
                    var dd = new Date(); // for now
                    var nowyear = dd.getFullYear(); // => 9
                    var nowmonth = dd.getMonth(); // => 9
                    var nowdate = dd.getDay(); // => 9


                    var yearAge = nowyear - birthyear;
                    console.log(yearAge);
                    //
                    if (nowmonth >= birthmonth) {
                        var monthAge = nowmonth - birthmonth;
                    }
                    else {
                        yearAge--;
                        var monthAge = 12 + nowmonth - birthmonth;
                    }

                    if (nowdate >= birthdate) {
                        var dateAge = nowdate - birthdate;
                    }
                    else {
                        monthAge--;
                        var dateAge = 31 + nowdate - birthdate;
                        if (monthAge < 0) {
                            monthAge = 11;
                            yearAge--;
                        }
                    }

                    $scope.risNewAppForm.age = yearAge + " years " + monthAge + " Months " + dateAge + " Day(s)";
                    console.log("Calculated " + yearAge + monthAge + dateAge);
                }
            }
            $scope.save = function () {
                var d = new Date();
                var n = d.getTime();
                $scope.risNewAppForm.regNo = n;
                $scope.risNewAppForm.applicationStatus = 1;

                if ($scope.risNewAppForm.OtherQuota != null) {
                    $scope.risNewAppForm.qouta = $scope.risNewAppForm.OtherQuota;
                }


                $scope.risNewAppForm.designation = $scope.risNewAppForm.designation.POSITION;
                console.log("Designation ========= : " + $scope.risNewAppForm.designation);
                $scope.risNewAppForm.circularNo = $scope.risNewAppForm.circularNo.CIRCULARNO;
                console.log($scope.risNewAppForm.circularNo);
                console.log($scope.risNewAppForm);
                if ($scope.risNewAppForm.id != null) {
                    console.log($scope.risNewAppForm.designation);
                    console.log("=========Coming Here========");
                    RisNewAppForm.update($scope.risNewAppForm, onSaveUpdated);
                    console.log("=========finishing Here========");
                } else {
                    RisNewAppForm.save($scope.risNewAppForm, onSaveFinished);
                }
            };


            $scope.clear = function () {
                $state.go('risNewAppForm', null, {reload: true});
            };

            $scope.currentStep = 'one';
            $scope.risNext = function (step) {
                $scope.currentStep = step;
                console.log($scope.currentStep);

                if ($scope.currentStep == "two") {
                    if ($stateParams.id != null) {
                        risNewAppFormTwoDetail.get({id: $stateParams.id}, function (result) {
                            $scope.risNewAppFormTwo = result;
                            console.log("===============for Edit man");
                            console.log($scope.risNewAppFormTwo);
                        });
                    } else {
                        console.log("No Id found");
                    }

                }
            }

            $scope.clear = function () {
                $state.go('risNewAppForm', null, {reload: true});
            };


            if ($stateParams.id) {
                $scope.load($stateParams.id);
            }
            else {
                $scope.items.push({
                    examName: '',
                    subject: '',
                    educationalInstitute: '',
                    passingYear: '',
                    boardUniversity: '',
                    additionalInformation: '',
                    experience: '',
                    qouta: ''
                });
            }

            $scope.add = function () {
                console.log("Hellow");
                $scope.items.push({
                    id: '',
                    examName: '',
                    subject: '',
                    educationalInstitute: '',
                    passingYear: '',
                    boardUniversity: '',
                    additionalInformation: '',
                    experience: '',
                    qouta: ''
                });
            };


            //ris new app form two ===============================================================
            $scope.abbreviate = function (text) {
                if (!angular.isString(text)) {
                    return '';
                }
                if (text.length < 30) {
                    return text;
                }
                return text ? (text.substring(0, 15) + '...' + text.slice(-10)) : '';
            };

            $scope.byteSize = function (base64String) {
                if (!angular.isString(base64String)) {
                    return '';
                }
                function endsWith(suffix, str) {
                    return str.indexOf(suffix, str.length - suffix.length) !== -1;
                }

                function paddingSize(base64String) {
                    if (endsWith('==', base64String)) {
                        return 2;
                    }
                    if (endsWith('=', base64String)) {
                        return 1;
                    }
                    return 0;
                }

                function size(base64String) {
                    return base64String.length / 4 * 3 - paddingSize(base64String);
                }

                function formatAsBytes(size) {
                    return size.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ") + " bytes";
                }

                return formatAsBytes(size(base64String));
            };

            $scope.setApplicantImg = function ($file, risNewAppForm) {
                if ($file) {
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL($file);
                    fileReader.onload = function (e) {
                        var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                        $scope.$apply(function () {
                            risNewAppForm.applicantImg = base64Data;
                            risNewAppForm.applicantImgContentType = $file.type;
                            risNewAppForm.applicantImgName = $file.name;
                        });
                    };
                }

            };

            $scope.getPosition = function (circularno) {
                $scope.pos = [];
                $scope.cicru = circularno;
                $scope.cicru = $scope.cicru.CIRCULARNO;
                console.log($scope.cicru);
                $scope.circular.circularNo = $scope.cicru;
                console.log($scope.circular);
                console.log($scope.circular.circularNo);
                $scope.pos = positionbycircular.get($scope.circular);
                console.log($scope.pos);

            }

            $scope.setSignature = function ($file, risNewAppFormTwo) {
                if ($file) {
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL($file);
                    fileReader.onload = function (e) {
                        var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                        $scope.$apply(function () {
                            risNewAppFormTwo.signature = base64Data;
                            risNewAppFormTwo.signatureContentType = $file.type;
                            risNewAppFormTwo.signatureImgName = $file.name;
                        });
                    };
                }

            };

            $scope.setBankInvoice = function ($file, risNewAppFormTwo) {
                if ($file) {
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL($file);
                    fileReader.onload = function (e) {
                        var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                        $scope.$apply(function () {
                            risNewAppFormTwo.bankInvoice = base64Data;
                            risNewAppFormTwo.bankInvoiceContentType = $file.type;
                            risNewAppFormTwo.bankInvoiceName = $file.name;
                        });
                    };
                }
            };

        }]);
