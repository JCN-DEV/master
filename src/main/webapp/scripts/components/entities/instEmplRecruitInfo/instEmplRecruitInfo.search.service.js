'use strict';

angular.module('stepApp')
    .factory('InstEmplRecruitInfoSearch', function ($resource) {
        return $resource('api/_search/instEmplRecruitInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('InstEmplRecruitInfoByEmp', function ($resource) {
        return $resource('api/instEmplRecruitInfos/instEmployee/:id', {}, {
            'query': { method: 'GET', isArray: false}
        });
    })
    .factory('InstEmplRecruitInfoCurrent', function ($resource) {
        return $resource('api/instEmplRecruitInfosCurrent', {}, {
            'get': { method: 'GET', isArray: false}
        });
    });
