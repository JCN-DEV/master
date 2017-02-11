'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('informationCorrection', {
                parent: 'entity',
                url: '/informationCorrections',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.informationCorrection.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/mpo/informationCorrection/informationCorrection/informationCorrections.html',
                        controller: 'InformationCorrectionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('informationCorrection');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('infoCorrectionApply', {
                parent: 'mpo',
                url: '/information-correction-apply',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/informationCorrection/informationCorrection/info-correction-application.html',
                        controller: 'InfoCorrectionAppController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('settings');
                        $translatePartialLoader.addPart('informationCorrection');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {status: null, id: null, job: null, user: null};
                    }
                }
            }).state('infoCorrectPendingList', {
                parent: 'mpo',
                url: '/information-correction-pending-list',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/informationCorrection/informationCorrection/info-correction-pending-list.html',
                        controller: 'InfoCorrectListByStatusController'
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
                    entity:['InformationCorrectionsByStatus', function(InformationCorrectionsByStatus) {
                        var data = InformationCorrectionsByStatus.query({status : 0});
                        data.pending = "pending";
                        return data;
                    }]
                }
            })
            .state('informationCorrection.detail', {
                parent: 'mpo',
                url: '/informationCorrection/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.informationCorrection.detail.title'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/informationCorrection/informationCorrection/informationCorrection-detail.html',
                        controller: 'InformationCorrectionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('informationCorrection');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InformationCorrection', function($stateParams, InformationCorrection) {
                        return InformationCorrection.get({id : $stateParams.id});
                    }]
                }
            })
            .state('approveInfoCorrection', {
                parent: 'informationCorrection.detail',
                url: '/info-correction/approve/:id',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/account/mpo/informationCorrection/informationCorrection/info-corr-approve-confirm-dialog.html',
                        controller: 'InfoCorrectApproveDialogController',
                        size: 'md',
                        resolve: {
                            entity: ['InformationCorrection','$stateParams', function( InformationCorrection, $stateParams) {
                                return InformationCorrection.get({id : $stateParams.id});
                            }]
                        }
                    })/*.result.then(function(result) {
                     $state.go('mpo.details');
                     }, function() {
                     $state.go('^');
                     })*/
                }]
            }).state('infoCorrectDeny', {
                parent: 'informationCorrection.detail',
                url: '/information-correction/deny/:id',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/account/mpo/informationCorrection/informationCorrection/info-correct-deny-confirm-dialog.html',
                        controller: 'InfoCorrectDenyDialogController',
                        size: 'md',
                        resolve: {
                            entity: ['InformationCorrection','$stateParams', function( InformationCorrection, $stateParams) {
                                return InformationCorrection.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                            $state.go('informationCorrection.detail', null, { reload: true });
                        }, function() {
                            $state.go('^');
                        })
                }]
            })
            .state('infoCorrectionChecklist', {
                parent: 'mpo',
                url: '/checklist/info-correction/{code}',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/informationCorrection/informationCorrection/info-correction-application-checklist-admin.html',
                        controller: 'InfoCorrectCheckListController'
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
                    }]

                }
            }).state('infoCorrectAppStatus', {
                parent: 'mpo',
                url: '/information-correction-Application-status',
                data: {
                    authorities: [],
                    pageTitle: 'global.menu.account.newApplication'
                },
                views: {
                    'mpoView@mpo': {
                        templateUrl: 'scripts/app/account/mpo/informationCorrection/informationCorrection/info-correct-application-status.html',
                        controller: 'InfoCorrectStatusController'
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
            .state('informationCorrection.new', {
                parent: 'informationCorrection',
                url: '/new',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/informationCorrection/informationCorrection-dialog.html',
                        controller: 'InformationCorrectionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    dob: null,
                                    name: null,
                                    indexNo: null,
                                    bankAccountNo: null,
                                    adForwarded: null,
                                    dgFinalApproval: null,
                                    createdDate: null,
                                    modifiedDate: null,
                                    directorComment: null,
                                    status: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('informationCorrection', null, { reload: true });
                    }, function() {
                        $state.go('informationCorrection');
                    })
                }]
            })
            .state('informationCorrection.edit', {
                parent: 'informationCorrection',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/informationCorrection/informationCorrection-dialog.html',
                        controller: 'InformationCorrectionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InformationCorrection', function(InformationCorrection) {
                                return InformationCorrection.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('informationCorrection', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('informationCorrection.delete', {
                parent: 'informationCorrection',
                url: '/{id}/delete',
                data: {
                    authorities: [],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/informationCorrection/informationCorrection-delete-dialog.html',
                        controller: 'InformationCorrectionDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InformationCorrection', function(InformationCorrection) {
                                return InformationCorrection.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('informationCorrection', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
