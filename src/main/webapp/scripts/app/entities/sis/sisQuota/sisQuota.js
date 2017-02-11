'use strict';

angular.module('stepApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('sisQuota', {
                parent: 'sis',
                url: '/sisQuotas',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.sisQuota.home.title'
                },
                views: {
                    'sisView@sis': {
                        templateUrl: 'scripts/app/entities/sis/sisQuota/sisQuotas.html',
                        controller: 'SisQuotaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sisQuota');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('sisQuota.detail', {
                parent: 'sis',
                url: '/sisQuota/{id}',
                data: {
                    authorities: [],
                    pageTitle: 'stepApp.sisQuota.detail.title'
                },
                views: {
                    'sisView@sis': {
                        templateUrl: 'scripts/app/entities/sis/sisQuota/sisQuota-detail.html',
                        controller: 'SisQuotaDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sisQuota');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SisQuota', function($stateParams, SisQuota) {
                        return SisQuota.get({id : $stateParams.id});
                    }]
                }
            })

            .state('sisQuota.new', {
                parent: 'sisQuota',
                url: '/new',
                data: {
                    authorities: []
                },

                views: {
                    'sisView@sis': {
                        templateUrl: 'scripts/app/entities/sis/sisQuota/sisQuota-dialog.html',
                        controller: 'SisQuotaDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sisQuota');
                        return $translate.refresh();
                    }],
                    entity: function () {
                        return {
                            name: null,
                            description: null,
                            isFor: true,
                            activeStatus: true
                        };
                    }
                }
            })
            .state('sisQuota.edit', {
                parent: 'sisQuota',
                url: '/{id}/edit',
                data: {
                    authorities: [],
                },
                views: {
                    'sisView@sis': {
                        templateUrl: 'scripts/app/entities/sis/sisQuota/sisQuota-dialog.html',
                        controller: 'SisQuotaDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sisQuota');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SisQuota', function($stateParams, SisQuota) {
                        return SisQuota.get({id : $stateParams.id});
                    }]
                }
            });
    });
