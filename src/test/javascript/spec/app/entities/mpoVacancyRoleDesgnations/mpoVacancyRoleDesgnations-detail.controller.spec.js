'use strict';

describe('MpoVacancyRoleDesgnations Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockMpoVacancyRoleDesgnations, MockMpoVacancyRole, MockUser, MockInstEmplDesignation, MockCmsSubject;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockMpoVacancyRoleDesgnations = jasmine.createSpy('MockMpoVacancyRoleDesgnations');
        MockMpoVacancyRole = jasmine.createSpy('MockMpoVacancyRole');
        MockUser = jasmine.createSpy('MockUser');
        MockInstEmplDesignation = jasmine.createSpy('MockInstEmplDesignation');
        MockCmsSubject = jasmine.createSpy('MockCmsSubject');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'MpoVacancyRoleDesgnations': MockMpoVacancyRoleDesgnations,
            'MpoVacancyRole': MockMpoVacancyRole,
            'User': MockUser,
            'InstEmplDesignation': MockInstEmplDesignation,
            'CmsSubject': MockCmsSubject
        };
        createController = function() {
            $injector.get('$controller')("MpoVacancyRoleDesgnationsDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:mpoVacancyRoleDesgnationsUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
