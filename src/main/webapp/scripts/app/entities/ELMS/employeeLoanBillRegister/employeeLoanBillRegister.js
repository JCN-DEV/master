'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('employeeLoanInfo.employeeLoanBillRegister', {
                parent: 'employeeLoanInfo',
                url: '/employeeLoanBillRegisters',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'stepApp.employeeLoanBillRegister.home.title'
                },
                views: {
                    'employeeLoanView@employeeLoanInfo': {
                        templateUrl: 'scripts/app/entities/ELMS/employeeLoanBillRegister/employeeLoanBillRegisters.html',
                        controller: 'EmployeeLoanBillRegisterController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employeeLoanBillRegister');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('employeeLoanInfo.employeeLoanBillRegister.detail', {
                parent: 'employeeLoanInfo.employeeLoanBillRegister',
                url: '/employeeLoanBillRegister/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.employeeLoanBillRegister.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/ELMS/employeeLoanBillRegister/employeeLoanBillRegister-detail.html',
                        controller: 'EmployeeLoanBillRegisterDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('employeeLoanBillRegister');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'EmployeeLoanBillRegister', function($stateParams, EmployeeLoanBillRegister) {
                        return EmployeeLoanBillRegister.get({id : $stateParams.id});
                    }]
                }
            })
            .state('employeeLoanInfo.employeeLoanBillRegister.new', {
                parent: 'employeeLoanInfo.employeeLoanBillRegister',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                 views: {
                    'employeeLoanView@employeeLoanInfo': {
                        templateUrl: 'scripts/app/entities/ELMS/employeeLoanBillRegister/employeeLoanBillRegister-dialog.html',
                        controller: 'EmployeeLoanBillRegisterDialogController'
                    }
                 },
                  resolve: {
                     entity: function () {
                         return {
                             applicationType: null,
                             billNo: null,
                             issueDate: null,
                             receiverName: null,
                             place: null,
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
            .state('employeeLoanInfo.employeeLoanBillRegister.edit', {
                parent: 'employeeLoanInfo.employeeLoanBillRegister',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/ELMS/employeeLoanBillRegister/employeeLoanBillRegister-dialog.html',
                        controller: 'EmployeeLoanBillRegisterDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['EmployeeLoanBillRegister', function(EmployeeLoanBillRegister) {
                                return EmployeeLoanBillRegister.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('employeeLoanBillRegister', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
