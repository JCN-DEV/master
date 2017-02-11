'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('instituteInfo.generalInfo', {
                parent: 'instituteInfo',
                url: '/general-info',
                data: {
                   // authorities: ['ROLE_USER'],
                    authorities: [],
                    pageTitle: 'stepApp.instGenInfo.detail.title'
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/GeneralInfo/instGenInfo-detail.html',
                        controller: 'InstGenInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'InstGenInfo', function($stateParams, InstGenInfo) {
                        //console.log(InstGenInfo.get({id : 0}));
                        return null;
                    }]
                }
            })
            .state('instituteInfo.generalInfo.new', {
                parent: 'instituteInfo',
                url: '/new-institute-registration',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl:'scripts/app/entities/instituteInformationSystem/GeneralInfo/instGenInfo-dialog.html',
                        controller: 'InstGenInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            code: null,
                            name: null,
                            publicationDate: null,
                            type: null,
                            village: null,
                            postOffice: null,
                            postCode: null,
                            landPhone: null,
                            mobileNo: null,
                            email: null,
                            faxNum: null,
                            website: null,
                            consArea: null,
                            category: null,
                            division: null,
                            firstRecognitionDate : null,
                            firstAffiliationDate : null,
                            lastExpireDateOfAffiliation : null,
                            dateOfMpo : null,
                            status : 0,
                            id: null
                        };
                    }
                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instGenInfo/instGenInfo-dialog.html',
                        controller: 'InstGenInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    code: null,
                                    name: null,
                                    publicationDate: null,
                                    type: null,
                                    village: null,
                                    postOffice: null,
                                    postCode: null,
                                    landPhone: null,
                                    mobileNo: null,
                                    email: null,
                                    consArea: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('instGenInfo', null, { reload: true });
                    }, function() {
                        $state.go('instGenInfo');
                    })
                }]*/
            })
            .state('instituteInfo.generalInfo.edit', {
                parent: 'instituteInfo.generalInfo',
                url: '/{id}/edit',
                data: {
                  //  authorities: ['ROLE_USER'],
                    authorities: []
                },
                 views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/GeneralInfo/instGenInfo-dialog.html',
                        controller: 'InstGenInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    entity: ['InstGenInfo','$stateParams', function(InstGenInfo,$stateParams) {
                        return InstGenInfo.get({id : $stateParams.id});
                    }]

                }
                /*onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instGenInfo/instGenInfo-dialog.html',
                        controller: 'InstGenInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['InstGenInfo', function(InstGenInfo) {
                                return InstGenInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instGenInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]*/
            })
            .state('instituteInfo.confirmationMsg', {
                parent: 'entity',
                url: '/confirmationMsg',
                data: {
                    //  authorities: ['ROLE_USER'],
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/GeneralInfo/confirmationMsg.html',
                        controller: 'InstGenInfoDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]


                }

            })
            .state('instituteInfo.generalInfo.delete', {
                parent: 'instituteInfo.generalInfo',
                url: '/{id}/delete',
                data: {
                    authorities: [],
                   // authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/GeneralInfo/instGenInfo-delete-dialog.html',
                        controller: 'InstGenInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['InstGenInfo', function(InstGenInfo) {
                                return InstGenInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instGenInfo.generalInfo', null, { reload: true });
                    }, function() {
                        $state.go('instGenInfo.generalInfo');
                    })
                }]

            }).state('instituteInfo.generalInfo.reject', {
                parent: 'instituteInfo.generalInfo',
                url: '/{id}/reject',
                data: {
                    authorities: [],
                   // authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/GeneralInfo/instGenInfo-reject-dialog.html',
                        controller: 'InstGenInfoRejectController',
                        size: 'md',
                        resolve: {
                            entity: ['InstGenInfo', function(InstGenInfo) {
                                return InstGenInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('instGenInfo.generalInfo', null, { reload: true });
                    }, function() {
                        $state.go('instGenInfo.generalInfo');
                    })
                }]

            })
            .state('instituteInfo.generalInfo.approve', {
                parent: 'instituteInfo.approve',
                url: '/{id}/general-info-approve',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/GeneralInfo/instGenInfo-approve-dialog.html',
                        controller: 'InstGenInfoApproveController',
                        size: 'md',
                        resolve: {
                            entity: ['InstGenInfo', function(InstGenInfo) {
                                return InstGenInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        console.log(result);
                            $state.go('instituteInfo.approve', null, { reload: true });
                    }, function() {
                        $state.go('instituteInfo.approve');
                    })

                }]

            })
            .state('instituteInfo.generalInfo.decline', {
                parent: 'instituteInfo.approve',
                url: '/{id}/general-info-decline',
                data: {
                  authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/institute-decline.html',
                        controller: 'InstGenInfoDeclineController',
                        size: 'md',
                        resolve: {
                            entity: ['InstGenInfo', function(InstGenInfo) {
                                return InstGenInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        console.log(result);
                            $state.go('instituteInfo.approve', null, { reload: true });
                    }, function() {
                        $state.go('instituteInfo.approve');
                    })

                }]

            })
            .state('instituteInfo.generalInfo.view', {
                parent: 'instituteInfo',
                url: '/general-info-view',
                data: {
                    // authorities: ['ROLE_USER'],
                    authorities: [],
                    pageTitle: 'stepApp.instGenInfo.detail.title'
                },
                views: {
                    'instituteView@instituteInfo': {
                        templateUrl: 'scripts/app/entities/instituteInformationSystem/GeneralInfo/instGenInfo-detail-view.html',
                        controller: 'InstGenInfoDetailViewController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('instGenInfo');
                        $translatePartialLoader.addPart('instType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })


    });
