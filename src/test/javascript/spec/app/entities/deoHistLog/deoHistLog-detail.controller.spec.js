'use strict';

describe('DeoHistLog Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockDeoHistLog, MockDeo, MockDistrict;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockDeoHistLog = jasmine.createSpy('MockDeoHistLog');
        MockDeo = jasmine.createSpy('MockDeo');
        MockDistrict = jasmine.createSpy('MockDistrict');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'DeoHistLog': MockDeoHistLog,
            'Deo': MockDeo,
            'District': MockDistrict
        };
        createController = function() {
            $injector.get('$controller')("DeoHistLogDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:deoHistLogUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
