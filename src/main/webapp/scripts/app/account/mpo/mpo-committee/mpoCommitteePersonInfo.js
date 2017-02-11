'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('allCommitteeMembers', {
                parent: 'entity',
                url: '/mpoCommitteeMembers',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.mpoCommitteePersonInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/mpo/mpo-committee/mpoCommitteeAllMembers.html',
                        controller: 'MpoCommitteeAllMembersController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoCommitteePersonInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            }).state('committeeDetails', {
                parent: 'entity',
                url: '/committee-members',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.mpoCommitteePersonInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/mpo/mpo-committee/mpoCommitteeMembers.html',
                        controller: 'MpoCommitteeMembersOfCommitteeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoCommitteePersonInfo');
                        $translatePartialLoader.addPart('mpoCommitteeHistory');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('mpoCommitteeAdd', {
                parent: 'entity',
                url: '/mpo/add-committede',

                data: {
                    authorities: ['ROLE_ADMIN','ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.mpoCommitteePersonInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/mpo/mpo-committee/mpoCommitteePersonInfo-dialog.html',
                        controller: 'MpoCommitteeDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoCommitteePersonInfo');
                        $translatePartialLoader.addPart('mpoCommitteeHistory');
                        $translatePartialLoader.addPart('gender');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MpoCommitteePersonInfo', function($stateParams, MpoCommitteePersonInfo) {
                        return null;
                    }]
                }
            }).state('mpoCommitteeFormation', {
                parent: 'entity',
                url: '/mpo/committe-formation',

                data: {
                    authorities: ['ROLE_ADMIN','ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.mpoCommitteePersonInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/mpo/mpo-committee/mpoCommitteeFormation-dialog.html',
                        controller: 'MpoCommitteeFormationDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoCommitteePersonInfo');
                        $translatePartialLoader.addPart('mpoCommitteeHistory');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MpoCommitteePersonInfo', function($stateParams, MpoCommitteePersonInfo) {
                        return null;
                    }]
                }
            }).state('mpoCommitteePersonInfo', {
                parent: 'entity',
                url: '/mpoCommitteePersonInfos',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.mpoCommitteePersonInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/mpo/mpo-committee/mpoCommitteePersonInfo/mpoCommitteePersonInfos.html',
                        controller: 'MpoCommitteePersonInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoCommitteePersonInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('mpoCommitteePersonInfo.detail', {
                parent: 'entity',
                url: '/mpoCommitteePersonInfo/{id}',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.mpoCommitteePersonInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/mpo/mpo-committee/mpoCommitteePersonInfo-detail.html',
                        controller: 'MpoCommitteePersonInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoCommitteePersonInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MpoCommitteePersonInfo', function($stateParams, MpoCommitteePersonInfo) {
                        return MpoCommitteePersonInfo.get({id : $stateParams.id});
                    }]
                }
            })/*.state('mpoCommitteePersonInfo.deactivate', {
                parent: 'entity',
                url: '/mpoCommitteePersonInfo/{id}',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.mpoCommitteePersonInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/mpo/mpo-committee/mpoCommitteePersonInfo-detail.html',
                        controller: 'MpoCommitteePersonInfoDeactivateController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoCommitteePersonInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MpoCommitteePersonInfo', function($stateParams, MpoCommitteePersonInfo) {
                        return MpoCommitteePersonInfo.get({id : $stateParams.id});
                    }]
                }
            })*/
            .state('mpoCommitteePersonInfo.new', {
                parent: 'mpoCommitteePersonInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_MPOADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/account/mpo/mpo-committee/mpoCommitteePersonInfo/mpoCommitteePersonInfo-dialog.html',
                        controller: 'MpoCommitteePersonInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    contactNo: null,
                                    address: null,
                                    designation: null,
                                    orgName: null,
                                    dateCrated: null,
                                    dateModified: null,
                                    status: null,
                                    activated: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                            $state.go('mpoCommitteePersonInfo', null, { reload: true });
                        }, function() {
                            $state.go('mpoCommitteePersonInfo');
                        })
                }]
            })
            .state('mpoCommitteePersonInfo.edit', {
                parent: 'mpoCommitteePersonInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_MPOADMIN'],
                    pageTitle: 'stepApp.mpoCommitteePersonInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/mpo/mpo-committee/mpoCommitteePersonInfo-dialog.html',
                        controller: 'MpoCommitteeDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mpoCommitteePersonInfo');
                        $translatePartialLoader.addPart('mpoCommitteeHistory');
                        $translatePartialLoader.addPart('gender');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MpoCommitteePersonInfo', function($stateParams, MpoCommitteePersonInfo) {
                        return null;
                    }]
                }
            })
            .state('mpoCommitteePersonInfo.delete', {
                parent: 'mpoCommitteePersonInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN','ROLE_MPOADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/account/mpo/mpo-committee/mpoCommitteePersonInfo/mpoCommitteePersonInfo-delete-dialog.html',
                        controller: 'MpoCommitteePersonInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['MpoCommitteePersonInfo', function(MpoCommitteePersonInfo) {
                                return MpoCommitteePersonInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                            $state.go('mpoCommitteePersonInfo', null, { reload: true });
                        }, function() {
                            $state.go('^');
                        })
                }]
            });

    });
