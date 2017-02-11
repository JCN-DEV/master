'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('employeeLoanInfo.employeeLoanRequisitionForm', {
                parent: 'employeeLoanInfo',
                url: '/employeeLoanRequisitionForms',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_INSTEMP','ROLE_USER'],
                    pageTitle: 'stepApp.employeeLoanRequisitionForm.home.title'
                },
                views: {
                    'employeeLoanView@employeeLoanInfo': {
                        templateUrl: 'scripts/app/entities/ELMS/employeeLoanRequisitionForm/employeeLoanRequisitionForms.html',
                        controller: 'EmployeeLoanRequisitionFormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employeeLoanRequisitionForm');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('employeeLoanInfo.employeeLoanRequisitionForm.detail', {
                parent: 'employeeLoanInfo.employeeLoanRequisitionForm',
                url: '/employeeLoanRequisitionForm/{id}',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_INSTEMP','ROLE_USER'],
                    pageTitle: 'stepApp.employeeLoanRequisitionForm.detail.title'
                },
                views: {
                    'employeeLoanView@employeeLoanInfo': {
                        templateUrl: 'scripts/app/entities/ELMS/employeeLoanRequisitionForm/employeeLoanRequisitionForm-detail.html',
                        controller: 'EmployeeLoanRequisitionFormDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employeeLoanRequisitionForm');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'EmployeeLoanRequisitionForm', function($stateParams, EmployeeLoanRequisitionForm) {
                        //return EmployeeLoanRequisitionForm.get({id : $stateParams.id});
                    }]
                }
            })
            .state('employeeLoanInfo.employeeLoanRequisitionForm.new', {
                parent: 'employeeLoanInfo.employeeLoanRequisitionForm',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_INSTEMP','ROLE_USER'],
                },views: {
                  'employeeLoanView@employeeLoanInfo': {
                      templateUrl: 'scripts/app/entities/ELMS/employeeLoanRequisitionForm/employeeLoanRequisitionForm-dialog.html',
                      controller: 'EmployeeLoanRequisitionFormDialogController'
                  }
                },
                resolve: {
                    entity: function () {
                        return {
                            loanRequisitionCode: null,
                            instituteEmployeeId: null,
                            amount: null,
                            installment: null,
                            approveStatus: null,
                            createDate: null,
                            createBy: null,
                            updateDate: null,
                            updateBy: null,
                            id: null
                        };
                    }
                }

            })
            .state('employeeLoanInfo.employeeLoanRequisitionForm.edit', {
                parent: 'employeeLoanInfo.employeeLoanRequisitionForm',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_INSTEMP','ROLE_USER'],
                },
                views: {
                  'employeeLoanView@employeeLoanInfo': {
                      templateUrl: 'scripts/app/entities/ELMS/employeeLoanRequisitionForm/employeeLoanRequisitionForm-edit.html',
                      controller: 'EmployeeLoanRequisitionFormDialogController',
                  }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                              $translatePartialLoader.addPart('employeeLoanRequisitionForm');
                              return $translate.refresh();
                    }],
                    entity: ['$stateParams','EmployeeLoanRequisitionForm', function($stateParams,EmployeeLoanRequisitionForm) {
                        return EmployeeLoanRequisitionForm.get({id : $stateParams.id});
                    }]
                }
            });
    });
