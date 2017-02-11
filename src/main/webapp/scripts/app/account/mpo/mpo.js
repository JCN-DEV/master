'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('mpo', {
                parent: 'account',
                url: '/mpo',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.mpo'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/mpo/mpo-system.html',
                        controller: 'MpoController'
                    },
                    'mpoView@mpo':{
                          templateUrl: 'scripts/app/account/mpo/mpo.html',
                          controller: 'MpoController'
                    }
                },

                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('training');
                        $translatePartialLoader.addPart('location');
                        $translatePartialLoader.addPart('applicantEducation');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('institute');
                        $translatePartialLoader.addPart('instituteType');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('staffCount');
                        $translatePartialLoader.addPart('employeeType');
                        $translatePartialLoader.addPart('mpo');
                        $translatePartialLoader.addPart('status');
                        return $translate.refresh();
                    }]
                }
            })
            .state('mpoNewEmployee', {
                parent: 'mpo',
                url: '/new-employee',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/account/mpo/mpo-employee-dialog.html',
                        controller: 'MPOEmployeeDialogController',
                        size: 'lg',
                        resolve: {
                            translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                $translatePartialLoader.addPart('applicantEducation');
                                $translatePartialLoader.addPart('employee');
                                $translatePartialLoader.addPart('payScale');
                                $translatePartialLoader.addPart('gender');
                                $translatePartialLoader.addPart('location');
                                $translatePartialLoader.addPart('mpo');
                                return $translate.refresh();
                            }],
                            entity: function () {
                                return {status: null, id: null, job: null, user: null};
                            }
                        }
                    }).result.then(function (result) {
                            $state.go('mpo', null, {reload: true});
                        }, function () {
                            $state.go('mpo');
                        })
                }]
            })
            .state('mpoNewEducation', {
                parent: 'mpo.details',
                url: '/new-education',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/account/mpo/mpo-education-dialog.html',
                        controller: 'MPOEmployeeEducationDialogController',
                        size: 'lg',
                        resolve: {
                            translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                $translatePartialLoader.addPart('employee');
                                $translatePartialLoader.addPart('payScale');
                                $translatePartialLoader.addPart('applicantEducation');
                                $translatePartialLoader.addPart('location');
                                $translatePartialLoader.addPart('mpo');
                                return $translate.refresh();
                            }],
                            entity: ['$stateParams', 'Employee', function($stateParams, Employee) {
                                return Employee.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('mpo.details', null, {reload: true});
                        }, function () {
                            $state.go('mpo.details');
                        })
                }]
            })
            .state('mpo.list', {
                parent: 'mpo',
                url: '/list',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.employee.home.title'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/mpo-list.html',
                        controller: 'MPOListController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('mpoApplication');
                        $translatePartialLoader.addPart('attachment');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('mpo.details', {
                parent: 'mpo',
                url: '/details/:id',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.employee.home.title'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/mpo-details.html',
                        controller: 'MPODetailsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('instEmplExperience');
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('iisCurriculumInfo');
                        $translatePartialLoader.addPart('iisCourseInfo');
                        $translatePartialLoader.addPart('instEmpCount');
                        $translatePartialLoader.addPart('instBuilding');
                        $translatePartialLoader.addPart('instLabInfo');
                        $translatePartialLoader.addPart('instShopInfo');
                        $translatePartialLoader.addPart('instLand');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('attachment');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Employee', function($stateParams, Employee) {
                        return Employee.get({id : $stateParams.id});
                    }]
                }
            })
            .state('timescaleDetails', {
                parent: 'mpo',
                url: '/timescale-details/:id',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.employee.home.title'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/timescale-details.html',
                        //the controller found in path >> scripts/app/account/mpo/mpo-details.controller.js
                        controller: 'TimescaleDetailsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('timeScaleApplication');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('attachment');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Employee', function($stateParams, Employee) {
                        //return Employee.get({id : $stateParams.id});
                        return null;
                    }]
                }
            })
            .state('apscaleDetails', {
                parent: 'mpo',
                url: '/ap-scale-details/:id',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.employee.home.title'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/assistantProfessorApplication/ap-details.html',
                        //the controller found in path : scripts/app/account/mpo/bed-details.controller.js
                        controller: 'ApDetailsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('aPScaleApplication');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('attachment');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Employee', function($stateParams, Employee) {
                        //return Employee.get({id : $stateParams.id});
                        return null;
                    }]
                }
            })
            .state('professorApplicationDetails', {
                parent: 'mpo',
                url: '/principle-application-details/:id',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.employee.home.title'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/professorApplication/professor-details.html',
                        controller: 'ProfessorDetailsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('professorApplication');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('attachment');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Employee', function($stateParams, Employee) {
                        //return Employee.get({id : $stateParams.id});
                        return null;
                    }]
                }
            }).state('nameCnclApplicationDetails', {
                parent: 'mpo',
                url: '/name-cancel-application-details/:id',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.employee.home.title'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/nameCancellation/name-cancellation-details.html',
                        controller: 'NameCnclDetailsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('attachment');
                        $translatePartialLoader.addPart('nameCnclApplication');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Employee', function($stateParams, Employee) {
                        //return Employee.get({id : $stateParams.id});
                        return null;
                    }]
                }
            })
            .state('bedDetails', {
                parent: 'mpo',
                url: '/bed-details/:id',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.employee.home.title'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/bed-details.html',
                        controller: 'BEdDetailsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('attachment');
                        $translatePartialLoader.addPart('bEdApplication');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Employee', function($stateParams, Employee) {
                        //return Employee.get({id : $stateParams.id});
                        return null;
                    }]
                }
            })
            .state('MpoApplicantEducationEdit', {
                parent: 'mpo.details',
                url: '/mpo-education-edit/:educationId',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/account/mpo/mpo-education-dialog.html',
                        controller: 'MPOEmployeeEducationDialogController',
                        size: 'lg',
                        resolve: {
                            translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                $translatePartialLoader.addPart('employee');
                                $translatePartialLoader.addPart('payScale');
                                $translatePartialLoader.addPart('applicantEducation');
                                $translatePartialLoader.addPart('location');
                                $translatePartialLoader.addPart('mpo');
                                return $translate.refresh();
                            }],
                            entity: ['$stateParams', 'ApplicantEducation', function($stateParams, ApplicantEducation) {
                                return ApplicantEducation.get({id : $stateParams.educationId});
                            }]
                        }
                    }).result.then(function (result) {
                        $state.go('mpo.details', null, {reload: true});
                    }, function () {
                        $state.go('mpo.details');
                    })
                }]
            })
            .state('mpo.new', {
                parent: 'mpo',
                url: '/new-application',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/mpo-application.html',
                        controller: 'NewApplicationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {status: null, id: null, job: null, user: null};
                    }
                }
            })
            .state('mpo.application', {
                parent: 'mpo',
                url: '/application',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/application.html',
                        controller: 'ApplicationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {status: null, id: null, job: null, user: null};
                    }
                }
            })
            .state('mpo.dashboard', {
                parent: 'mpo',
                url: '/dashboard',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/dashboard.html',
                        controller: 'ApplicationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {status: null, id: null, job: null, user: null};
                    }
                }
            })

            .state('mpo.payScalePendingList', {
                parent: 'mpo',
                url: '/pay-scale-pending-list',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpopayscalemanagement/mpo-pay-scale-list.html',
                        controller: 'PayScaleListByStatusController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity:['MpoApplicationPayScale', function(MpoApplicationPayScale) {
                        var data = MpoApplicationPayScale.query({status : 0});
                        data.pending = "pending";
                        return data;
                    }]
                }
            }).state('mpo.timeScalepayCodePendingList', {
                parent: 'mpo',
                url: '/time-scale-pending-list',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpopayscalemanagement/timescale-paycode-pending-or-approve-list.html',
                        controller: 'TimeScalePaycodeListByStatusController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity:['TimescaleApplicationList', function(TimescaleApplicationList) {
                        var data = TimescaleApplicationList.query({status : 0});
                        data.pending = "pending";
                        console.log(data);
                        return data;
                    }]
                }

            }).state('mpo.bedpayCodePendingList', {
                parent: 'mpo',
                url: '/bed-pending-list-pay-scale-assign',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpopayscalemanagement/bed-paycode-pending-or-approve-list.html',
                        controller: 'BedPaycodeListByStatusController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity:['BEdApplicationList', function(BEdApplicationList) {
                        var data = BEdApplicationList.query({status : 0});
                        data.pending = "pending";
                        console.log(data);
                        return data;
                    }]
                }
            }).state('mpo.appayCodePendingList', {
                parent: 'mpo',
                url: '/ap-payscale-pending-list',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpopayscalemanagement/ap-paycode-pending-or-approve-list.html',
                        controller: 'ApPaycodeListByStatusController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity:['APcaleApplicationList', function(APcaleApplicationList) {
                        var data = APcaleApplicationList.query({status : 0});
                        data.pending = "pending";
                        console.log(data);
                        return data;
                    }]
                }
            }).state('mpo.principalpayCodePendingList', {
                parent: 'mpo',
                url: '/principal-pending-list',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpopayscalemanagement/principal-paycode-pending-or-approve-list.html',
                        controller: 'PrincipalPaycodeListByStatusController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity:['PrincipleApplicationList', function(PrincipleApplicationList) {
                        var data = PrincipleApplicationList.query({status : 0});
                        data.pending = "pending";
                        console.log(data);
                        return data;
                    }]
                }
            })
            .state('mpo.payScaleApprovedList', {
                parent: 'mpo',
                url: '/pay-scale-approved-list',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpopayscalemanagement/mpo-pay-scale-list.html',
                        controller: 'PayScaleListByStatusController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity:['MpoApplicationPayScale', function(MpoApplicationPayScale) {
                        var data = MpoApplicationPayScale.query({page:0, size:10000, status : 1});
                        data.pending = "approved";
                        return data;
                    }]
                }
            }).state('mpo.timeScalepayCodeApprovedList', {
                parent: 'mpo',
                url: '/time-scale-pay-scale-approved-list',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpopayscalemanagement/timescale-paycode-pending-or-approve-list.html',
                        controller: 'TimeScalePaycodeListByStatusController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity:['TimescaleApplicationList', function(TimescaleApplicationList) {
                        var data = TimescaleApplicationList.query({status : 1});
                        console.log(data);
                        data.pending = "approved";
                        return data;
                    }]
                }

            }).state('mpo.bedpayCodeApprovedList', {
                parent: 'mpo',
                url: '/bed-payscale-approved-list',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpopayscalemanagement/bed-paycode-pending-or-approve-list.html',
                        controller: 'BedPaycodeListByStatusController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity:['BEdApplicationList', function(BEdApplicationList) {
                        var data = BEdApplicationList.query({status : 1});
                        console.log(data);
                        data.pending = "approved";
                        return data;
                    }]
                }
            }).state('mpo.appayCodeApprovedList', {
                parent: 'mpo',
                url: '/ap-payscale-approved-list',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpopayscalemanagement/ap-paycode-pending-or-approve-list.html',
                        controller: 'ApPaycodeListByStatusController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity:['APcaleApplicationList', function(APcaleApplicationList) {
                        var data = APcaleApplicationList.query({status : 1});
                        console.log(data);
                        data.pending = "approved";
                        return data;
                    }]
                }
            }).state('mpo.principalpayCodeApprovedList', {
                parent: 'mpo',
                url: '/principal-payscale-approved-list',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpopayscalemanagement/principal-paycode-pending-or-approve-list.html',
                        controller: 'PrincipalPaycodeListByStatusController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity:['PrincipleApplicationList', function(PrincipleApplicationList) {
                        var data = PrincipleApplicationList.query({status : 1});
                        console.log(data);
                        data.pending = "approved";
                        return data;
                    }]
                }
            })
            .state('mpo.payScaleAssign', {
                parent: 'mpo',
                url: '/{id}/payScaleAssign',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpopayscalemanagement/pay-scale-assign.html',
                        controller: 'PayScaleAssignController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity: ['MpoApplication','$stateParams', function( MpoApplication, $stateParams) {
                        return MpoApplication.get({id : $stateParams.id});
                    }]
                }
            })
            .state('mpo.timescalePaycodeAssign', {
                parent: 'mpo',
                url: '/{id}/times-cale-pay-code',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpopayscalemanagement/timescale-paycode-assign.html',
                        controller: 'TimeScalePayCodeAssignController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity: ['TimeScaleApplication','$stateParams', function( TimeScaleApplication, $stateParams) {
                        return TimeScaleApplication.get({id : $stateParams.id});
                    }]
                }

            }).state('mpo.bedPaycodeAssign', {
                parent: 'mpo',
                url: '/{id}/bed-pay-code',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpopayscalemanagement/bed-paycode-assign.html',
                        controller: 'BedPayCodeAssignController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity: ['BEdApplication','$stateParams', function( BEdApplication, $stateParams) {
                        return BEdApplication.get({id : $stateParams.id});
                    }]
                }
            }).state('mpo.apPaycodeAssign', {
                parent: 'mpo',
                url: '/{id}/ap-pay-code',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpopayscalemanagement/ap-paycode-assign.html',
                        controller: 'ApPayCodeAssignController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity: ['APScaleApplication','$stateParams', function( APScaleApplication, $stateParams) {
                        return APScaleApplication.get({id : $stateParams.id});
                    }]
                }
            })
            .state('mpo.principalPaycodeAssign', {
                parent: 'mpo',
                url: '/{id}/principal-pay-code',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpopayscalemanagement/principal-paycode-assign.html',
                        controller: 'PrincipalPayCodeAssignController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity: ['ProfessorApplication','$stateParams', function( ProfessorApplication, $stateParams) {
                        return ProfessorApplication.get({id : $stateParams.id});
                    }]
                }
            })
            .state('mpo.salaryGenerate', {
                parent: 'mpo',
                url: '/{id}/salaryGenerate',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/salary-generate.html',
                        controller: 'SalaryGenerateController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity: ['MpoApplication','$stateParams', function( MpoApplication, $stateParams) {
                        return MpoApplication.get({id : $stateParams.id});
                    }]
                }
            })
            .state('mpo.salaryGenerateByInstitute', {
                parent: 'mpo',
                url: '/salaryGenerateByInstitute',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/salary-generate-by-institute.html',
                        controller: 'SalaryGenerateByInstituteController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity: ['SalaryGenerateByInstitute','$stateParams', function( SalaryGenerateByInstitute, $stateParams) {
                        return SalaryGenerateByInstitute.query();
                    }]
                }
            })
            .state('mpo.employeeTrack', {
                parent: 'mpo',
                url: '/track-employee',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/mpo-application-track.html',
                        controller: 'MpoApplicationTrack'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('attachment');
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        $translatePartialLoader.addPart('attachment');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {status: null, id: null, job: null, user: null};
                    }
                }
            })
            .state('mpo.checklist', {
                parent: 'mpo',
                url: '/{code}/checklist',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/mpo-application-checklist-admin.html',
                        controller: 'MpoApplicationCheckListController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity: ['MpoApplicationLogEmployeeCode','$stateParams', function( MpoApplicationLogEmployeeCode, $stateParams) {
                        return MpoApplicationLogEmployeeCode.get({'code':$stateParams.code});
                    }]
                }
            })
            .state('timescale-checklist', {
                parent: 'mpo',
                url: '/{code}/timescale-checklist',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/timescale-application-checklist.html',
                        controller: 'TimescaleApplicationCheckListController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity: ['MpoApplicationLogEmployeeCode','$stateParams', function( MpoApplicationLogEmployeeCode, $stateParams) {
                        return MpoApplicationLogEmployeeCode.get({'code':$stateParams.code});
                    }]
                }
            }).state('nameCncl-checklist', {
                parent: 'mpo',
                url: '/{code}/nameCncl-checklist',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/nameCancellation/name-cancellation-checklist.html',
                        controller: 'NameCnclApplicationCheckListController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity: ['MpoApplicationLogEmployeeCode','$stateParams', function( MpoApplicationLogEmployeeCode, $stateParams) {
                        return MpoApplicationLogEmployeeCode.get({'code':$stateParams.code});
                    }]
                }
            }).state('nameCncl-statusLog', {
                parent: 'mpo',
                url: '/{code}/nameCncl-statusLog',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/nameCancellation/name-cancellation-application-status.html',
                        controller: 'NameCancelStatusLogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('attachment');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity: ['MpoApplicationLogEmployeeCode','$stateParams', function( MpoApplicationLogEmployeeCode, $stateParams) {
                        return MpoApplicationLogEmployeeCode.get({'code':$stateParams.code});
                    }]
                }
            })
            .state('ap-checklist', {
                parent: 'mpo',
                url: '/{code}/ap-checklist',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/assistantProfessorApplication/ap-application-checklist.html',
                        controller: 'ApApplicationCheckListController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity: ['MpoApplicationLogEmployeeCode','$stateParams', function( MpoApplicationLogEmployeeCode, $stateParams) {
                        return MpoApplicationLogEmployeeCode.get({'code':$stateParams.code});
                    }]
                }
            })
            .state('professor-checklist', {
                parent: 'mpo',
                url: '/{code}/professor-checklist',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/assistantProfessorApplication/professor-application-checklist.html',
                        controller: 'ProfessorApplicationCheckListController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity: ['MpoApplicationLogEmployeeCode','$stateParams', function( MpoApplicationLogEmployeeCode, $stateParams) {
                        return MpoApplicationLogEmployeeCode.get({'code':$stateParams.code});
                    }]
                }
            })
            .state('bed-checklist', {
                parent: 'mpo',
                url: '/{code}/bed-checklist',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/bed-application-checklist.html',
                        controller: 'BEdApplicationCheckListController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity: ['MpoApplicationLogEmployeeCode','$stateParams', function( MpoApplicationLogEmployeeCode, $stateParams) {
                        return MpoApplicationLogEmployeeCode.get({'code':$stateParams.code});
                    }]
                }
            })
            .state('mpo.bed', {
                parent: 'mpo',
                url: '/bed-application',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/bed-application.html',
                        controller: 'MPOBedApplicationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {status: null, id: null, job: null, user: null};
                    }
                }
            })
            .state('mpo.bedApplication', {
                parent: 'mpo',
                url: '/bEd-apply',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/bed-application.html',
                        controller: 'BEdAppController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('instEmplTraining');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        $translatePartialLoader.addPart('timeScaleApplication');
                        $translatePartialLoader.addPart('bEdApplication');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {status: null, id: null, job: null, user: null};
                    }
                }
            })
            .state('mpo.timescaleApplication', {
                parent: 'mpo',
                url: '/timescale-apply',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/timescale-application.html',
                        controller: 'TimeScaleApplicationControllerss'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        $translatePartialLoader.addPart('timeScaleApplication');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {status: null, id: null, job: null, user: null};
                    }
                }
            })
            .state('mpo.ApApplication', {
                parent: 'mpo',
                url: '/apscale-apply',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/assistantProfessorApplication/ap-application.html',
                        controller: 'ApApplicationControllers'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        $translatePartialLoader.addPart('timeScaleApplication');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {status: null, id: null, job: null, user: null};
                    }
                }
            }).state('mpo.name-cancel-application', {
                parent: 'mpo',
                url: '/name-cancel-apply',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/nameCancellation/name-cancellation-application.html',
                        controller: 'NameCancelApplicationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        $translatePartialLoader.addPart('timeScaleApplication');
                        $translatePartialLoader.addPart('nameCnclApplication');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {status: null, id: null, job: null, user: null};
                    }
                }
            })/*.state('mpo.ApApplication', {
                parent: 'mpo',
                url: '/apscale-apply',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/assistantProfessorApplication/ap-application.html',
                        controller: 'ApApplicationControllers'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        $translatePartialLoader.addPart('timeScaleApplication');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {status: null, id: null, job: null, user: null};
                    }
                }
            })*/
            .state('mpo.ProfessorApplication', {
                parent: 'mpo',
                url: '/professorscale-apply',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/professorApplication/professor-application.html',
                        controller: 'ProfessorApplicationControllers'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        $translatePartialLoader.addPart('timeScaleApplication');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {status: null, id: null, job: null, user: null};
                    }
                }
            })
            .state('mpo.payscale', {
                parent: 'mpo',
                url: '/payscale-application',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/timescale-application.html',
                        controller: 'MPOPayScaleApplicationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {status: null, id: null, job: null, user: null};
                    }
                }
            })
            .state('MpoApplicantEducationDelete', {
                parent: 'mpo.details',
                url: '/mpo-education-delete/:educationId',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/account/mpo/mpo-education-delete-dialog.html',
                        controller: 'MpoEducationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ApplicantEducation', function(ApplicantEducation) {
                                return ApplicantEducation.get({id : $stateParams.educationId});
                            }]
                        }
                    }).result.then(function(result) {
                            $state.go('mpo.details', null, { reload: true });
                        }, function() {
                            $state.go('^');
                        })
                }]
            })
            .state('mpoPendingList', {
                parent: 'mpo',
                url: '/mpo-pending-list',
                data: {
                    authorities: []
                },
                views:{
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/mpo-pending-list.html',
                        controller: 'MpoListByStatus'
                    }
                },
                resolve:{
                    entity:['MpoApplicationList', function(MpoApplicationList) {
                        var data = MpoApplicationList.query({status : 0});
                        data.pending = "pending";
                        return data;
                    }]
                }
            }).state('mpoPendingListCommittee', {
                parent: 'mpo',
                url: '/mpo-pending-list-committee',
                data: {
                    authorities: []
                },
                views:{
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/mpo-pending-list-committee.html',
                        controller: 'MpoListCommittee'
                    }
                },
                resolve:{
                    entity:['MpoApplicationList', function(MpoApplicationList) {
                        var data = MpoApplicationList.query({status : 0});
                        data.pending = "pending";
                        return data;
                    }]
                }
            }).state('mpoPendingApprovedList', {
                parent: 'mpo',
                url: '/mpo-final-list',
                data: {
                    authorities: []
                },
                views:{
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/mpo-pending-list-final.html',
                        controller: 'MpoFinalListToApprove'
                    }
                }
                /*resolve:{
                    entity:['MpoApplicationFinalList', function(MpoApplicationFinalList) {
                        var data = MpoApplicationFinalList.query();
                        data.pending = "pending";
                        return data;
                    }]
                }*/
            })
            .state('timescalePendingList', {
                parent: 'mpo',
                url: '/timescale-pending-list',
                data: {
                    authorities: []
                },
                views:{
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/timescale-pending-list.html',
                        controller: 'TimescaleListController'
                    }
                },
                resolve:{
                    entity:['TimescaleApplicationList', function(TimescaleApplicationList) {
                        var data = TimescaleApplicationList.query({status : 0});
                        console.log(">>>>>>>>>>>>>>");
                        console.log(data);
                        data.pending = "pending";
                        return data;
                    }]
                }
            })
            .state('timescalePendingApList', {
                            parent: 'mpo',
                            url: '/timescale-pending-list-ap',
                            data: {
                                authorities: []
                            },
                            views:{
                                'mpoView@mpo': {
                                    templateUrl: 'scripts/app/account/mpo/timescale-pending-list-ap.html',
                                    controller: 'TimescaleListApController'
                                }
                            },
                            resolve:{
                                entity:['TimescaleApplicationList', function(TimescaleApplicationList) {
                                    var data = TimescaleApApplicationList.query({status : 0});
                                    console.log(">>>>>>>>>>>>>>");
                                    console.log(data);
                                    data.pending = "pending";
                                    return data;
                                }]
                            }
                        })
            .state('apPendingList', {
                parent: 'mpo',
                url: '/ap-pending-list',
                data: {
                    authorities: []
                },
                views:{
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/assistantProfessorApplication/ap-pending-list.html',
                        controller: 'ApListController'
                    }
                },
                resolve:{
                    entity:['APcaleApplicationList', function(APcaleApplicationList) {
                        var data = APcaleApplicationList.query({status : 0});
                        console.log(data);
                        data.pending = "pending";
                        return data;
                    }]
                }
            })
            .state('principalPendingList', {
                parent: 'mpo',
                url: '/principal-application-pending-list',
                data: {
                    authorities: []
                },
                views:{
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/professorApplication/professor-pending-list.html',
                        controller: 'ProfessorListController'
                    }
                },
                resolve:{
                    entity:['PrincipleApplicationList', function(PrincipleApplicationList) {
                        var data = PrincipleApplicationList.query({status : 0});
                        data.pending = "pending";
                        return data;
                    }]
                }
            }).state('nameCancelPendingList', {
                parent: 'mpo',
                url: '/name-cancel-application-pending-list',
                data: {
                    authorities: []
                },
                views:{
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/nameCancellation/name-cancellation-pending-list.html',
                        controller: 'NameCancelListController'
                    }
                },
                resolve:{
                    entity:['NameCnclApplicationList', function(NameCnclApplicationList) {
                        var data = NameCnclApplicationList.query({status : 0});
                        data.pending = "pending";
                        return data;
                    }]
                }
            })
            .state('nameCancelApprovedList', {
                parent: 'mpo',
                url: '/name-cancel-application-approved-list',
                data: {
                    authorities: []
                },
                views:{
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/nameCancellation/name-cancellation-approved-list.html',
                        controller: 'NameCancelListController'
                    }
                },
                resolve:{
                    entity:['NameCnclApplicationList', function(NameCnclApplicationList) {
                        var data = NameCnclApplicationList.query({status : 1});
                        data.pending = "pending";
                        return data;
                    }]
                }
            }).state('instituteNameCancelList', {
                parent: 'mpo',
                url: '/institute-name-cancel-applications',
                data: {
                    authorities: []
                },
                views:{
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/nameCancellation/name-cancellation-list-institute.html',
                        controller: 'NameCancelListForInstituteController'
                    }
                },
                /*resolve:{
                    entity:['NameCnclApplicationList', function(NameCnclApplicationList) {
                        var data = NameCnclApplicationList.query({status : 1});
                        data.pending = "pending";
                        return data;
                    }]
                }*/
            })
            .state('bEdPendingList', {
                parent: 'mpo',
                url: '/bed-pending-list',
                data: {
                    authorities: []
                },
                views:{
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/bed-pending-list.html',
                        controller: 'BEdListController'
                    }
                },
                resolve:{
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bEdApplication');

                        return $translate.refresh();
                    }],
                    entity:['BEdApplicationList', function(BEdApplicationList) {
                        var data = BEdApplicationList.query({status : 0});
                        data.pending = "pending";
                        return data;
                    }]
                }

            })


            .state('mpoApprovedList', {
                parent: 'mpo',
                url: '/mpo-approved-list',
                data: {
                    authorities: []
                },
                views:{
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/mpo-approved-list.html',
                        controller: 'MpoListByStatus'
                    }
                },
                resolve:{
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity:['MpoApplicationList', function(MpoApplicationList) {
                        var data = MpoApplicationList.query({status : 1});

                        console.log(data);
                        data.pending = "approved";
                        return data;
                    }]
                }
            })
            .state('apApprovedList', {
                parent: 'mpo',
                url: '/ap-approved-list',
                data: {
                    authorities: []
                },
                views:{
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/assistantProfessorApplication/ap-approved-list.html',
                        controller: 'ApListController'
                    }
                },
                resolve:{
                    entity:['APcaleApplicationList', function(APcaleApplicationList) {
                        var data = APcaleApplicationList.query({status : 1});
                        data.pending = "approved";
                        return data;
                    }]
                }
            })
            .state('timescaleApprovedList', {
                parent: 'mpo',
                url: '/timescale-approved-list',
                data: {
                    authorities: []
                },
                views:{
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/timescale-approved-list.html',
                        controller: 'TimescaleListController'
                    }
                },
                resolve:{
                    entity:['TimescaleApplicationList', function(TimescaleApplicationList) {
                        var data = TimescaleApplicationList.query({status : 1});
                        data.pending = "approved";
                        return data;
                    }]
                }
            })
            .state('principalApprovedList', {
                parent: 'mpo',
                url: '/principal-application-approved-list',
                data: {
                    authorities: []
                },
                views:{
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/professorApplication/professor-approved-list.html',
                        controller: 'ProfessorListController'
                    }
                },
                resolve:{
                    entity:['PrincipleApplicationList', function(PrincipleApplicationList) {
                        var data = PrincipleApplicationList.query({status : 1});
                        data.pending = "approved";
                        return data;
                    }]
                }
            })
            .state('bEdApprovedList', {
                parent: 'mpo',
                url: '/bed-approved-list',
                data: {
                    authorities: []
                },
                views:{
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/bed-approved-list.html',
                        controller: 'BEdListController'
                    }
                },
                resolve:{
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bEdApplication');
                        return $translate.refresh();
                    }],
                    entity:['BEdApplicationList', function(BEdApplicationList) {
                        var data = BEdApplicationList.query({status : 1});
                        data.pending = "approved";
                        return data;
                    }]
                }
            })
            .state('MpoTrainingDelete', {
                parent: 'mpo.details',
                url: '/mpo-training-delete/:trainingId',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/account/mpo/mpo-training-delete-dialog.html',
                        controller: 'MPOTrainingDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Training', function(Training) {
                                return Training.get({id : $stateParams.trainingId});
                            }]
                        }
                    }).result.then(function(result) {
                            $state.go('mpo.details', null, { reload: true });
                        }, function() {
                            $state.go('^');
                        })
                }]
            })
            .state('mpoApplicantTrainingEdit', {
            parent: 'mpo.details',
            url: '/mpo-training-edit/:trainingId',
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                $modal.open({
                    templateUrl: 'scripts/app/account/mpo/mpo-training-dialog.html',
                    controller: 'MPOTrainingDialogController',
                    size: 'lg',
                    resolve: {
                        entity: ['Training', function(Training) {
                            return Training.get({id : $stateParams.trainingId});
                        }]
                    }
                }).result.then(function(result) {
                    $state.go('mpo.details', null, { reload: true });
                }, function() {
                    $state.go('^');
                })
            }]
        })
        .state('MpoAttachmentDelete', {
            parent: 'mpo.details',
            url: '/mpo-attachment-delete/:attachmentId',
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                $modal.open({
                    templateUrl: 'scripts/app/account/mpo/mpo-attachment-delete-dialog.html',
                    controller: 'MpoAttachmentDeleteController',
                    size: 'md',
                    resolve: {
                        entity: ['Attachment', function(Attachment) {
                            return Attachment.get({id : $stateParams.attachmentId});
                        }]
                    }
                }).result.then(function(result) {
                        $state.go('mpo.details', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
            }]
        })
        .state('mpoApprove', {
            parent: 'mpo.details',
            url: '/approve/:id',
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                $modal.open({
                    templateUrl: 'scripts/app/account/mpo/mpo-approve-confirm-dialog.html',
                    controller: 'MpoApproveDialogController',
                    size: 'md',
                    resolve: {
                        entity: ['MpoApplication','$stateParams', function( MpoApplication, $stateParams) {
                            return MpoApplication.get({id : $stateParams.id});
                        }]
                    }
                })/*.result.then(function(result) {
                        $state.go('mpo.details');
                    }, function() {
                        $state.go('^');
                    })*/
            }]
        }).state('mpoApproveAll', {
            parent: 'mpoPendingList',
            url: '/approve-all-mpo',
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                $modal.open({
                    templateUrl: 'scripts/app/account/mpo/mpo-approve-all-confirm-dialog.html',
                    controller: 'MpoApproveAllDialogController',
                    size: 'md',
                    resolve: {
                       /* entity: ['MpoApplication','$stateParams', function( MpoApplication, $stateParams) {
                            return MpoApplication.get({id : $stateParams.id});
                        }]*/
                    }
                })/*.result.then(function(result) {
                        $state.go('mpo.details');
                    }, function() {
                        $state.go('^');
                    })*/
            }]
        })
            .state('timescaleApprove', {
                parent: 'timescaleDetails',
                url: '/timescaleApprove/:id',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/account/mpo/timescale-approve-confirm-dialog.html',
                        controller: 'TimescaleApproveDialogController',
                        size: 'md',
                        resolve: {
                            entity: ['TimeScaleApplication','$stateParams', function( TimeScaleApplication, $stateParams) {
                                return TimeScaleApplication.get({id : $stateParams.id});
                            }]
                        }
                    })/*.result.then(function(result) {
                            $state.go('timescaleDetails', null, { reload: true });
                        }, function() {
                            $state.go('^');
                        })*/
                }]
            }).state('timescaleApproveAll', {
                parent: 'timescalePendingList',
                url: '/timescaleApprove-all',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/account/mpo/timescale-approve-all-confirm-dialog.html',
                        controller: 'TimescaleApproveAllDialogController',
                        size: 'md',
                        resolve: {
                           /* entity: ['TimeScaleApplication','$stateParams', function( TimeScaleApplication, $stateParams) {
                                return TimeScaleApplication.get({id : $stateParams.id});
                            }]*/
                        }
                    })/*.result.then(function(result) {
                            $state.go('timescaleDetails', null, { reload: true });
                        }, function() {
                            $state.go('^');
                        })*/
                }]
            }).state('bedAppApprove', {
                parent: 'bedDetails',
                url: '/BED-Application-approve/:id',
                data: {
                    authorities: []
                },
                params: {
                    bedInfo: null
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/account/mpo/bed-approve-confirm-dialog.html',
                        controller: 'BEDApproveDialogController',
                        size: 'md',
                        resolve: {
                            /*entity: ['TimeScaleApplication','$stateParams', function( TimeScaleApplication, $stateParams) {
                                return TimeScaleApplication.get({id : $stateParams.id});
                            }]*/
                        }
                    })/*.result.then(function(result) {
                            console.log("result :"+result);
                            $state.go('bedDetails');
                        }, function() {
                            $state.go('^');
                        })*/
                }]
            })
            .state('apApprove', {
                parent: 'apscaleDetails',
                url: '/ap-Approve/:id',
                data: {
                    authorities: []
                },
                params: {
                    apAll: null
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/account/assistantProfessorApplication/ap-approve-confirm-dialog.html',
                        controller: 'ApApproveDialogController',
                        size: 'md',
                        resolve: {
                            /*entity: ['TimeScaleApplication','$stateParams', function( TimeScaleApplication, $stateParams) {
                                return TimeScaleApplication.get({id : $stateParams.id});
                            }]*/
                        }
                    })/*.result.then(function(result) {
                            $state.go('apscaleDetails');
                        }, function() {
                            $state.go('^');
                        })*/
                }]
            })
            .state('professorApprove', {
                parent: 'professorApplicationDetails',
                url: '/principal-Approve/:id',
                data: {
                    authorities: []
                },params: {
                    apAll: null
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/account/professorApplication/professor-approve-confirm-dialog.html',
                        controller: 'ProfessorApproveDialogController',
                        size: 'md',
                        resolve: {
                            /*entity: ['TimeScaleApplication','$stateParams', function( TimeScaleApplication, $stateParams) {
                                return TimeScaleApplication.get({id : $stateParams.id});
                            }]*/
                        }
                    })/*.result.then(function(result) {
                            $state.go('professorApplicationDetails');
                        }, function() {
                            $state.go('^');
                        })*/
                }]
            }).state('nameCnclAppApprove', {
                parent: 'nameCnclApplicationDetails',
                url: '/name-cancel-application-Approve/:id',
                data: {
                    authorities: []
                },params: {
                    apAll: null
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/account/nameCancellation/name-cancellation-approve-confirm-dialog.html',
                        controller: 'NameCancellationApproveDialogController',
                        size: 'md',
                        resolve: {
                            /*entity: ['TimeScaleApplication','$stateParams', function( TimeScaleApplication, $stateParams) {
                                return TimeScaleApplication.get({id : $stateParams.id});
                            }]*/
                        }
                    })/*.result.then(function(result) {
                            $state.go('professorApplicationDetails');
                        }, function() {
                            $state.go('^');
                        })*/
                }]
            })
        .state('mpoDeny', {
            parent: 'mpo.details',
            url: '/deny/:id',
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                $modal.open({
                    templateUrl: 'scripts/app/account/mpo/mpo-deny-confirm-dialog.html',
                    controller: 'MpoDenyDialogController',
                    size: 'md',
                    resolve: {
                        entity: ['MpoApplication','$stateParams', function( MpoApplication, $stateParams) {
                            return MpoApplication.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function(result) {
                        $state.go('mpo.details', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
            }]
        }).state('nameCnclDeny', {
            parent: 'mpo.details',
            url: '/nameCnclDeny/:id',
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                $modal.open({
                    templateUrl: 'scripts/app/account/mpo/mpo-deny-confirm-dialog.html',
                    controller: 'MpoDenyDialogController',
                    size: 'md',
                    resolve: {
                        entity: ['MpoApplication','$stateParams', function( MpoApplication, $stateParams) {
                            return MpoApplication.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function(result) {
                        $state.go('mpo.details', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
            }]
        })
        .state('bedDeny', {
            parent: 'bedDetails',
            url: '/deny/:id',
            data: {
                authorities: []
            },params: {
                    bedInfo: null
                },
            onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                $modal.open({
                    templateUrl: 'scripts/app/account/mpo/bed-deny-confirm-dialog.html',
                    controller: 'BEDDenyDialogController',
                    size: 'md',
                    resolve: {
                        /*entity: ['MpoApplication','$stateParams', function( MpoApplication, $stateParams) {
                            return MpoApplication.get({id : $stateParams.id});
                        }]*/
                    }
                }).result.then(function(result) {
                        $state.go('bedDetails', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
            }]
        })
            .state('timescaleDeny', {
                parent: 'timescaleDetails',
                url: '/timescale-deny/:id',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/account/mpo/timescale-deny-confirm-dialog.html',
                        controller: 'TimescaleDenyDialogController',
                        size: 'md',
                        resolve: {
                            entity: ['TimeScaleApplication','$stateParams', function( TimeScaleApplication, $stateParams) {
                                return TimeScaleApplication.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                            $state.go('timescaleDetails', null, { reload: true });
                        }, function() {
                            $state.go('^');
                        })
                }]
            })
            .state('apDeny', {
                parent: 'apscaleDetails',
                url: '/ap-deny/:id',
                data: {
                    authorities: []
                },
                params: {
                    apAll: null
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/account/assistantProfessorApplication/ap-deny-confirm-dialog.html',
                        controller: 'ApDenyDialogController',
                        size: 'md',
                        resolve: {
                           /* entity: ['TimeScaleApplication','$stateParams', function( TimeScaleApplication, $stateParams) {
                                return TimeScaleApplication.get({id : $stateParams.id});
                            }]*/
                        }
                    }).result.then(function(result) {
                            //$state.go('apscaleDetails', null, { reload: true });
                            $state.go('apscaleDetails');
                        }, function() {
                            $state.go('^');
                        })
                }]
            })
            .state('professorDeny', {
                parent: 'professorApplicationDetails',
                url: '/professor-deny/:id',
                data: {
                    authorities: []
                },
                params: {
                    apAll: null
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/account/professorApplication/professor-deny-confirm-dialog.html',
                        controller: 'ProfessorDenyDialogController',
                        size: 'md',
                        resolve: {
                            /*entity: ['TimeScaleApplication','$stateParams', function( TimeScaleApplication, $stateParams) {
                                return TimeScaleApplication.get({id : $stateParams.id});
                            }]*/
                        }
                    }).result.then(function(result) {
                            $state.go('professorApplicationDetails');
                        }, function() {
                            $state.go('^');
                        })
                }]
            }).state('mpo.bedApplicationStatus', {
                parent: 'mpo',
                url: '/BED-Application-status',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/application-status.html',
                        controller: 'BedApplicationStatusController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {status: null, id: null, job: null, user: null};
                    }
                }
            }).state('mpo.timescaleApplicationStatus', {
                parent: 'mpo',
                url: '/timescale-Application-status',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/application-status.html',
                        // controller found in path  >> scripts/app/account/mpo/bed-application-checklist.controller.js
                        controller: 'TimescaleApplicationStatusController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {status: null, id: null, job: null, user: null};
                    }
                }
            }).state('mpo.apScaleApplicationStatus', {
                parent: 'mpo',
                url: '/Assistant-Professor-Application-status',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/application-status.html',
                        controller: 'ApApplicationStatusController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {status: null, id: null, job: null, user: null};
                    }
                }
            }).state('PrincipalApplicationStatus', {
                parent: 'mpo',
                url: '/principal-Application-status',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/application-status.html',
                        controller: 'PrincipalApplicationStatusController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {status: null, id: null, job: null, user: null};
                    }
                }
            }).state('mpo.processSalary', {
                parent: 'mpo',
                url: '/process-salary',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/mpo-salary-process.html',
                        controller: 'MpoSalaryProcessController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('employee');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('payScale');
                        $translatePartialLoader.addPart('gender');
                        $translatePartialLoader.addPart('sessions');
                        $translatePartialLoader.addPart('mpo');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {status: null, id: null, job: null, user: null};
                    }
                }
            }).state('mpo.disburse', {
                              parent: 'mpo',
                              url: '/disburse',
                              data: {
                                  authorities: [],
                                  pageTitle: 'global.menu.account.newApplication'
                              },
                              views: {
                                  'mpoView@mpo': {
                                      templateUrl: 'scripts/app/account/mpo/mpo-salary-disburse.html',
                                      controller: 'MpoSalaryDisburseController'
                                  }
                              },
                              resolve: {
                                  translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                                      $translatePartialLoader.addPart('settings');
                                      $translatePartialLoader.addPart('employee');
                                      $translatePartialLoader.addPart('global');
                                      $translatePartialLoader.addPart('payScale');
                                      $translatePartialLoader.addPart('gender');
                                      $translatePartialLoader.addPart('sessions');
                                      $translatePartialLoader.addPart('mpo');
                                      return $translate.refresh();
                                  }],
                                  entity: function () {
                                      return {status: null, id: null, job: null, user: null};
                                  }
                              }
                          });
    });
