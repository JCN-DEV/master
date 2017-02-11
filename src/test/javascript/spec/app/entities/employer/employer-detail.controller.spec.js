'use strict';

describe('Employer Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockEmployer, MockUser, MockCountry;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockEmployer = jasmine.createSpy('MockEmployer');
        MockUser = jasmine.createSpy('MockUser');
        MockCountry = jasmine.createSpy('MockCountry');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Employer': MockEmployer,
            'User': MockUser,
            'Country': MockCountry
        };
        createController = function() {
            $injector.get('$controller')("EmployerDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:employerUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
