'use strict';

describe('MpoApplication Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockMpoApplication, MockEmployee, MockInstitute;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockMpoApplication = jasmine.createSpy('MockMpoApplication');
        MockEmployee = jasmine.createSpy('MockEmployee');
        MockInstitute = jasmine.createSpy('MockInstitute');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'MpoApplication': MockMpoApplication,
            'Employee': MockEmployee,
            'Institute': MockInstitute
        };
        createController = function() {
            $injector.get('$controller')("MpoApplicationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:mpoApplicationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
