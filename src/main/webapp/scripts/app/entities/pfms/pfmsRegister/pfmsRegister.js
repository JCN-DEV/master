'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pfmsRegister', {
                parent: 'pfms',
                url: '/pfmsRegisters',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pfmsRegister.home.title',
                    displayName: 'Register'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsRegister/pfmsRegisters.html',
                        controller: 'PfmsRegisterController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsRegister');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('pfmsRegister.detail', {
                parent: 'pfms',
                url: '/pfmsRegister/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'stepApp.pfmsRegister.detail.title',
                    displayName: 'Register Details'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsRegister/pfmsRegister-detail.html',
                        controller: 'PfmsRegisterDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsRegister');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PfmsRegister', function($stateParams, PfmsRegister) {
                        return PfmsRegister.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pfmsRegister.new', {
                parent: 'pfmsRegister',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                    displayName: 'New Register'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsRegister/pfmsRegister-dialog.html',
                        controller: 'PfmsRegisterDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsRegister');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            applicationType: null,
                            isBillRegister: true,
                            billNo: null,
                            billIssueDate: null,
                            billReceiverName: null,
                            billPlace: null,
                            billDate: null,
                            noOfWithdrawal: null,
                            checkNo: null,
                            checkDate: null,
                            checkTokenNo: null,
                        };
                    }
                }
            })
            .state('pfmsRegister.edit', {
                parent: 'pfmsRegister',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                    displayName: 'Edit Register details'
                },
                views: {
                    'pfmsView@pfms': {
                        templateUrl: 'scripts/app/entities/pfms/pfmsRegister/pfmsRegister-dialog.html',
                        controller: 'PfmsRegisterDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pfmsRegister');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PfmsRegister', function($stateParams, PfmsRegister) {
                        //return PfmsRegister.get({id : $stateParams.id});
                    }]
                }
            });
    });
