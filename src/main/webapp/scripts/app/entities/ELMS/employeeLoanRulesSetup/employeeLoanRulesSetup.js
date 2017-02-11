'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('employeeLoanInfo.employeeLoanRulesSetup', {
                parent: 'employeeLoanInfo',
                url: '/employeeLoanRulesSetups',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.employeeLoanRulesSetup.home.title'
                },
                views: {
                    'employeeLoanView@employeeLoanInfo': {
                        templateUrl: 'scripts/app/entities/ELMS/employeeLoanRulesSetup/employeeLoanRulesSetups.html',
                        controller: 'EmployeeLoanRulesSetupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employeeLoanRulesSetup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('employeeLoanInfo.employeeLoanRulesSetup.detail', {
                parent: 'employeeLoanInfo.employeeLoanRulesSetup',
                url: '/employeeLoanRulesSetup/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.employeeLoanRulesSetup.detail.title'
                },
                views: {
                    'employeeLoanView@employeeLoanInfo': {
                        templateUrl: 'scripts/app/entities/ELMS/employeeLoanRulesSetup/employeeLoanRulesSetup-detail.html',
                        controller: 'EmployeeLoanRulesSetupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employeeLoanRulesSetup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'EmployeeLoanRulesSetup', function($stateParams, EmployeeLoanRulesSetup) {
                        return EmployeeLoanRulesSetup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('employeeLoanInfo.employeeLoanRulesSetup.new', {
                parent: 'employeeLoanInfo.employeeLoanRulesSetup',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                views: {
                  'employeeLoanView@employeeLoanInfo': {
                      templateUrl: 'scripts/app/entities/ELMS/employeeLoanRulesSetup/employeeLoanRulesSetup-dialog.html',
                      controller: 'EmployeeLoanRulesSetupDialogController',
                  }
                },
                resolve: {
                    entity: function () {
                        return {
                            loanName: null,
                            loanRulesDescription: null,
                            maximumWithdrawal: null,
                            minimumAmountBasic: null,
                            interest: null,
                            status: null,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }
            })
            .state('employeeLoanInfo.employeeLoanRulesSetup.edit', {
                parent: 'employeeLoanInfo.employeeLoanRulesSetup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                views: {
                  'employeeLoanView@employeeLoanInfo': {
                      templateUrl: 'scripts/app/entities/ELMS/employeeLoanRulesSetup/employeeLoanRulesSetup-dialog.html',
                      controller: 'EmployeeLoanRulesSetupDialogController',
                  }
                },
                resolve: {
                  translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                      $translatePartialLoader.addPart('employeeLoanRulesSetup');
                      return $translate.refresh();
                  }],
                  entity: ['$stateParams', 'EmployeeLoanRulesSetup', function($stateParams, EmployeeLoanRulesSetup) {
                      return EmployeeLoanRulesSetup.get({id : $stateParams.id});
                  }]
                }
            });
    });
