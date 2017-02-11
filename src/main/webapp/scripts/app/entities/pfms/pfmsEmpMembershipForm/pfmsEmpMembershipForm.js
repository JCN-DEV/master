'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pfmsEmpMembershipForm', {
                parent: 'pfms',
                url: '/pfmsEmpMembershipForms',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pfmsEmpMembershipForm.home.title',
                    displayName: 'Employee Membership Forms'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsEmpMembershipForm/pfmsEmpMembershipForms.html',
                        controller: 'PfmsEmpMembershipFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsEmpMembershipForm');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pfmsEmpMembershipForm.detail', {
                parent: 'pfms',
                url: '/pfmsEmpMembershipForm/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pfmsEmpMembershipForm.detail.title',
                    displayName: 'Employee Membership Details'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsEmpMembershipForm/pfmsEmpMembershipForm-detail.html',
                        controller: 'PfmsEmpMembershipFormDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsEmpMembershipForm');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PfmsEmpMembershipForm', function($stateParams, PfmsEmpMembershipForm) {
                        return PfmsEmpMembershipForm.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pfmsEmpMembershipForm.new', {
                parent: 'pfmsEmpMembershipForm',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                    displayName: 'Employee Membership new'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsEmpMembershipForm/pfmsEmpMembershipForm-dialog.html',
                        controller: 'PfmsEmpMembershipFormDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsEmpMembershipForm');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            isMinimumThreeYrs: false,
                            isMandatoryContribute: false,
                            isAnotherContFund: false,
                            fundName: null,
                            isEmpFamily: false,
                            percentOfDeduct: null,
                            isMoneySection: false,
                            nomineeName: null,
                            ageOfNominee: null,
                            nomineeAddress: null,
                            witnessNameOne: null,
                            witnessMobileNoOne: null,
                            witnessAddressOne: null,
                            witnessNameTwo: null,
                            witnessMobileNoTwo: null,
                            witnessAddressTwo: null,
                            stationName: null,
                            applicationDate: null,
                            remarks: null,
                            activeStatus: true,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('pfmsEmpMembershipForm.edit', {
                parent: 'pfmsEmpMembershipForm',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                    displayName: 'Edit employee Membership'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsEmpMembershipForm/pfmsEmpMembershipForm-dialog.html',
                        controller: 'PfmsEmpMembershipFormDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsEmpMembershipForm');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PfmsEmpMembershipForm', function($stateParams, PfmsEmpMembershipForm) {
                        //return PfmsEmpMembershipForm.get({id : $stateParams.id});
                    }]
                }
            });
    });
