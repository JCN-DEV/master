'use strict';

angular.module('stepApp')
    .factory('MpoVacancyRoleSearch', function ($resource) {
        return $resource('api/_search/mpoVacancyRoles/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
